package sample.persistent.dao;

import sample.persistent.po.TblPerson;
import org.springframework.stereotype.Repository;
import io.github.xinyangpan.persistent.dao.impl.ActiveableTraceableDao;

@Repository
public class TblPersonDao extends ActiveableTraceableDao<TblPerson, Long, Long> {

}