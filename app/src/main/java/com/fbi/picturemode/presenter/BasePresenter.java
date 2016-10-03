package com.fbi.picturemode.presenter;

import com.fbi.picturemode.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public abstract class BasePresenter<T extends BaseView> {

  private T view;
  private List<Subscription> subscriptions = new ArrayList<>();

  public BasePresenter(T baseView) {
    this.view = baseView;
    initModel();
  }

  public T getView() {
    return view;
  }

  public List<Subscription> getSubscriptions() {
    return this.subscriptions;
  }

  protected abstract void initModel();


  public void onDestroy() {
    for (Subscription subscription : subscriptions) {
      if (subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
    }
    this.view = null;
  }
}
