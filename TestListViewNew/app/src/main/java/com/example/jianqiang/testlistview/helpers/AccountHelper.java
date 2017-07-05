package com.example.jianqiang.testlistview.helpers;

public class AccountHelper
{
    private volatile static AccountHelper sInstance;

    private User user;

    public AccountHelper()
    {
        user = new User("包建强");
    }

    public static AccountHelper getInstance()
    {
        if(sInstance == null)
        {
            synchronized(AccountHelper.class)
            {
                if(sInstance == null)
                {
                    sInstance = new AccountHelper();
                }
            }
        }

        return sInstance;
    }

    public String getAccountName()
    {
        return user.getUserName();
    }

    public static class User
    {
        private String userName;

        User(String userName)
        {
            this.userName = userName;
        }

        public String getUserName()
        {
            return userName;
        }
    }
}
