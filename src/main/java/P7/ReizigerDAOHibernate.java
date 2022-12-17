package P7;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;


    public ReizigerDAOHibernate(Session session) {
        this.session = session;

    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
            Transaction transaction = this.session.beginTransaction();

            session.save(reiziger);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.update(reiziger);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        Transaction transaction = this.session.beginTransaction();
        try{
            if(reiziger.getAdres() != null){
                session.delete(reiziger.getAdres());
            }

            if (reiziger.getOvChipkaartList().isEmpty()){
                for (OVChipkaart ov : reiziger.getOvChipkaartList()){
                    session.delete(ov);
                }
            }

            session.delete(reiziger);
            transaction.commit();
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try{
            Reiziger reizigerBijId = session.createQuery("from Reiziger where reiziger_id =" + id,
                    Reiziger.class).getSingleResult();
            return reizigerBijId;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        try{
            Query<Reiziger> reizigerQuery = session.createQuery("from " +
                    "Reiziger where geboortedatum =  :datum", Reiziger.class);
            reizigerQuery.setParameter("datum", Date.valueOf(datum));
            return reizigerQuery.getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try{
            List<Reiziger> reizigerList = session.createQuery("FROM Reiziger ",Reiziger.class).getResultList();
            return reizigerList;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
