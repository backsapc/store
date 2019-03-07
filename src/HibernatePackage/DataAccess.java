package HibernatePackage;

import Beans.UserBean;
import Entities.CheckoutEntity;
import Entities.CheckoutDetailsEntity;
import Entities.CommentEntity;
import Entities.UserEntity;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class DataAccess {

    public static UserEntity getUser(String username){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            UserEntity user = session.get(UserEntity.class, username);
            tx.commit();
            return user;

        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            return null;
        }
        finally {
            session.close();
        }
    }

    public static boolean saveUser(UserEntity user){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        return saveObjectToDb(user, session, tx);
    }

    private static boolean saveObjectToDb(Object object, Session session, Transaction tx) {
        try {
            session.save(object);
            tx.commit();
            return true;
        } catch (Exception ex) {
            tx.rollback();
            return false;
        }
        finally {
            session.close();
        }
    }

    public static int updateUser(UserBean user){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("update UserEntity set username =:nameParam," +
                    " defaulttab =:defaultTabParam");
            query.setParameter("nameParam", user.getName());
            query.setParameter("defaultTabParam", user.getDefaultTab() );
            int result = query.executeUpdate();
            tx.commit();
            return result;
        } catch (Exception ex) {
            tx.rollback();
            return 0;
        }
        finally {
            session.close();
        }
    }

    public static boolean saveCheckout(CheckoutEntity checkout){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        return saveObjectToDb(checkout, session, tx);
    }

    public static CheckoutEntity getOrder(int orderId){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            CheckoutEntity checkoutEntity = session.get(CheckoutEntity.class, orderId);
            tx.commit();
            return checkoutEntity;

        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            return null;
        }
        finally {
            session.close();
        }
    }

    public static List<CheckoutEntity> getOrders(UserEntity userEntity){
        return getOrders(userEntity.getUsername());
    }

    public static List<CheckoutEntity> getOrders(String username){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from CheckoutEntity where username =:paramName");
            query.setParameter("paramName", username);
            List list = query.list();
            tx.commit();
            return new LinkedList<>(list);

        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            return null;
        }
        finally {
            session.close();
        }
    }

    public static boolean saveCheckoutDetails(CheckoutDetailsEntity checkoutDetails){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        return saveObjectToDb(checkoutDetails, session, tx);
    }

    public static List<CheckoutDetailsEntity> getCheckoutDetails(CheckoutEntity checkout){
        return getCheckoutDetails(checkout.getId());
    }

    public static List<CheckoutDetailsEntity> getCheckoutDetails(int orderId){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from CheckoutDetailsEntity where orderId =:paramName");
            query.setParameter("paramName", orderId);
            List list = query.list();
            tx.commit();
            return new LinkedList<>(list);

        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            return null;
        }
        finally {
            session.close();
        }
    }


    public static boolean saveComment(CommentEntity commentEntity){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        return saveObjectToDb(commentEntity, session, tx);
    }

    public static List<CommentEntity> getComments(UserEntity userEntity){
        return getComments(userEntity.getUsername());
    }

    public static List<CommentEntity> getComments(String username){
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from CommentEntity where username =:paramName");
            query.setParameter("paramName", username);
            List list = query.list();
            tx.commit();
            return new LinkedList<>(list);
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            return null;
        }
        finally {
            session.close();
        }
    }

    public static void deleteComments(){
        throw new NotImplementedException();
    }
}
