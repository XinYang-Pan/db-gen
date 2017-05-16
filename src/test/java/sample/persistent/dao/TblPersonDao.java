package sample.persistent.dao;

import org.springframework.stereotype.Repository;
import sample.persistent.po.TblPerson;
import io.github.xinyangpan.persistent.dao.impl.ActiveableTraceableDao;

@Repository
public class TblPersonDao extends ActiveableTraceableDao<TblPerson, Long, Long> {

}