package com.cc.learn.netty.recycle;

import io.netty.util.Recycler;

/**
 * 轻量级对象回收站
 *
 * @author wangchen
 * @createDate 2021/03/31
 */
public class RecycleDemo {

    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    static final class User{
        private String name;
        private Recycler.Handle<User> handle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public void recycle(){
            handle.recycle(this);
        }
    }

    public static void main(String[] args) {
        User user = userRecycler.get(); //从对象池获取User对象
        user.setName("hello");
        user.recycle();//回收对象到对象池
        User user1 = userRecycler.get();//从对象池获取User对象
        System.out.println(user1.getName());
        System.out.println(user == user1);
    }
}
