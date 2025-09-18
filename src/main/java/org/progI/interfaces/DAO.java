package org.progI.interfaces;

import java.util.List;

public interface DAO<O, K> {
  //O de Objeto, K de Key (clave primaria)

  public List<O> getAll();

  public void insert(O objeto);

  public void update(O objeto);

  public void delete(K id);

  public O getById(K id);

  public boolean existsById(K id);

}
