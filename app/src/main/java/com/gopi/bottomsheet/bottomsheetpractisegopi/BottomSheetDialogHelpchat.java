package com.gopi.bottomsheet.bottomsheetpractisegopi;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.gopi.bottomsheet.bottomsheetpractisegopi.utils.RxJavaFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gopikrishna on 6/3/16.
 */
public class BottomSheetDialogHelpchat extends BottomSheetDialog {

    static ChooserArrayAdapter chooserArrayAdapter;

    // Add other necessary apps to show in BottomSheet
    private static String[] packagesSupportedList = new String[]{"com.google.android.apps.plus","com.google.android.talk",
            "com.whatsapp",
            "com.linkedin.android",
            "com.facebook.orca", "com.facebook.katana", "com.twitter.android"};

    public BottomSheetDialogHelpchat(Context context) {
        super(context);
    }

    public static void shareFunctionalityDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.sheet, null);
        GridView gridView = ((GridView) view.findViewById(R.id.bottom_sheet_listview_new));
        CoordinatorLayout coordinatorLayout = null;
        final List<ResolveInfo> resInfosNew = new ArrayList<>();
        List<String> packagesList = new ArrayList<>();
        chooserArrayAdapter = new ChooserArrayAdapter(context, packagesList);
        gridView.setAdapter(chooserArrayAdapter);

        processShareFunctionality(context, resInfosNew, packagesList, view, gridView,false,coordinatorLayout);

    }

    private static void processShareFunctionality(final Context context,final List<ResolveInfo> resInfosNew,final List<String> packagesList,final View view,final GridView gridView,final boolean isShowAllApps,final CoordinatorLayout coordinatorLayout) {
        Observable<Object> objectObservable = RxJavaFactory.makeObservable(refreshInBackground(context, resInfosNew, packagesList, isShowAllApps));
        objectObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("completed called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("BottomSheetDialogHelpchat.onError");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("BottomSheetDialogHelpchat.onNext");
                        chooserArrayAdapter.notifyDataSetChanged();
                        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
                        mBottomSheetDialog.setContentView(view);
                        View parentView = ((View) view.getParent());
                        parentView.setFitsSystemWindows(true);
                        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((parentView)));

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                       /* view.measure(0, 0);
                        bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());

                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parentView.getLayoutParams();
                        *//*if (params.getBehavior() instanceof BottomSheetBehavior) {
                            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
                        }*//*
                        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                        parentView.setLayoutParams(params);*/

//                        bottomSheetBehavior.onTouchEvent(null,view.getParent(),Mot)
//                        bottomSheetBehavior.onInterceptTouchEvent()
                        /*mBottomSheetDialog.setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });*/

//                        bottomSheetBehavior.onNestedScrollAccepted(coordinatorLayout,);
                       // mBottomSheetDialog.setCanceledOnTouchOutside(false);
                        mBottomSheetDialog.show();

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int item, long id) {

                                ResolveInfo resolveInfo = resInfosNew.get(item);
                                if (!resolveInfo.activityInfo.packageName.equals("ShareAll")) {
                                    invokeApplication(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name, context);
                                } else {
                                    //Refresh the adapter with All Shareable items
                                    processShareFunctionality(context, resInfosNew, packagesList, view, gridView, true,coordinatorLayout);
                                }

                            }
                        });
                    }
                });
    }

    private static Callable refreshInBackground(final Context context, final List<ResolveInfo> resInfosNew, final List<String> packagesList,final boolean isShowAllApps) {
        return new Callable<Void>(){
            @Override
            public Void call() throws Exception {
                resInfosNew.clear();
                packagesList.clear();

                resInfosNew.addAll(getShareHelpchatPackageResolveInfos(context,isShowAllApps));
                packagesList.addAll(getPackagesList(resInfosNew));
                return null;
            }
        };
    }

    // Convert ResolveInfos to the List of String package names
    private static List<String> getPackagesList(List<ResolveInfo> resolveInfoList) {
        List<String> packagesList = new ArrayList<>();
        for (int i = 0; i < resolveInfoList.size(); i++) {
            packagesList.add(resolveInfoList.get(i).activityInfo.packageName);
        }
        return packagesList;
    }

    private static List<ResolveInfo> getShareHelpchatPackageResolveInfos(Context context,boolean isShowAllApps) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        final List<ResolveInfo> resInfos = context.getPackageManager().queryIntentActivities(shareIntent, 0);
        List<ResolveInfo> resolveInfoList = new ArrayList<>();
        if (!isShowAllApps) {
            Set<String> hashSet = new HashSet<>(8);
            hashSet.addAll(Arrays.asList(packagesSupportedList));
            if (!resInfos.isEmpty()) {
                for (ResolveInfo resInfo : resInfos) {
                    String packageName = resInfo.activityInfo.packageName;
                    if (hashSet.contains(packageName) || packageName.contains("mail") || packageName.contains("messaging")) {
                        resolveInfoList.add(resInfo);
                    }
                }
            }
            addShareAllItem(resolveInfoList);
        } else {
            resolveInfoList.addAll(resInfos);
        }
        return resolveInfoList;
    }

    private static void addShareAllItem(List<ResolveInfo> resolveInfoList) {
        ResolveInfo resolveInfo = new ResolveInfo();
        resolveInfo.activityInfo = new ActivityInfo();
        resolveInfo.activityInfo.packageName = "ShareAll";
        resolveInfoList.add(resolveInfo);
    }

    private static void invokeApplication(String packageName, String activityNameInfo,Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityNameInfo));
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Helpchat Share Text ");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Helpchat Share Subject");
        intent.setPackage(packageName);
        context.startActivity(intent);
    }
}
