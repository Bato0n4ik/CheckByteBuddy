package com.andrew.Interceptors;


import jakarta.transaction.Transactional;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.lang.reflect.Method;
import java.util.concurrent.Callable;



public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    public TransactionInterceptor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @RuntimeType
    public Object intercept(@SuperCall Callable<Object> callable, @Origin Method method) throws Exception {
        Object result = null;
        boolean openTransaction =  false;
        Transaction transaction = null;
        if(method.isAnnotationPresent(Transactional.class)) {
            transaction =  sessionFactory.getCurrentSession().getTransaction();
            if(!transaction.isActive()) {
                transaction.begin();
                openTransaction = true;
            }
        }
        try{
            result = callable.call();
            if(openTransaction) {
                transaction.commit();
            }
        }
        catch(Exception e){
            if(openTransaction){
                transaction.rollback();
            }
            throw e;
        }
        return result;
    }
}
