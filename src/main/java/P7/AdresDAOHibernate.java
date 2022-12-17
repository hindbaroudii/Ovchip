package P7;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO{

    private final Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.save(adres);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean update(Adres adres) {
        Transaction transaction = this.session.beginTransaction();

        try {
            session.update(adres);
            transaction.commit();
            return true;

        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean delete(Adres adres) {
        Transaction transaction = this.session.beginTransaction();
        try {
            session.delete(adres);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Adres adres = session.createQuery("FROM Adres WHERE reiziger_id = "+ reiziger.getReiziger_id(), Adres.class).getSingleResult();
            return adres;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<Adres> findAll() {
        try{
            List<Adres> adresList = session.createQuery("FROM Adres", Adres.class).getResultList();
            return adresList;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }
}
