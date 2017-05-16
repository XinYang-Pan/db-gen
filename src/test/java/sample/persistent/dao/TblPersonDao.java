package sample.persistent.dao;

import org.springframework.stereotype.Repository;
import io.github.xinyangpan.persistent.dao.impl.ActiveableTraceableDao;
import sample.persistent.po.TblPerson;

@Repository
public class TblPersonDao extends ActiveableTraceableDao<TblPerson, Long, Long> {

}