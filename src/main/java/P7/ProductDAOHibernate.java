package P7;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{

    private Session session;

    public ProductDAOHibernate(Session session){
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.save(product);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.update(product);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.delete(product);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        try{
            List<Product> productList = session.createQuery("from Product", Product.class).getResultList();
            return productList;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
