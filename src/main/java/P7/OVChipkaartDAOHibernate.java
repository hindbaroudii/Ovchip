package P7;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Transaction transaction = this.session.beginTransaction();
        try{
            session.save(ovChipkaart);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        Transaction transaction = this.session.beginTransaction();

        try {
            session.update(ovChipkaart);
            transaction.commit();
            return true;

        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        Transaction transaction = this.session.beginTransaction();
        try {
            session.delete(ovChipkaart);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<OVChipkaart> ovChipkaartList  = session.createQuery("FROM OVChipkaart WHERE reiziger_id = "+ reiziger.getReiziger_id(), OVChipkaart.class).getResultList();
            return ovChipkaartList;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try{
            List<OVChipkaart> ovChipkaartList = session.createQuery("FROM OVChipkaart ", OVChipkaart.class).getResultList();
            return ovChipkaartList;
        }catch (Exception e){
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }
}
