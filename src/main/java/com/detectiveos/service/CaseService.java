package com.detectiveos.service;
import com.detectiveos.exception.CaseNotFoundException;
import com.detectiveos.model.*;
import com.detectiveos.repository.CaseRepository;
import com.detectiveos.util.ValidationUtil;
import java.util.List;
public class CaseService {
    private final CaseRepository repo=new CaseRepository();
    public List<CaseFile> all(){return repo.findAll();}
    public CaseFile get(Long id) throws CaseNotFoundException {CaseFile c=repo.find(id); if(c==null) throw new CaseNotFoundException("Case #"+id+" not found."); return c;}
    public CaseFile save(CaseFile c){ValidationUtil.required(c.getTitle(),"Case title");ValidationUtil.required(c.getLocation(),"Location");return repo.save(c);}
    public CaseFile update(CaseFile c){return repo.update(c);}
}
