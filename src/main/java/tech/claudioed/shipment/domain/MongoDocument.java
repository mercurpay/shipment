package tech.claudioed.shipment.domain;

import org.bson.Document;

/** @author claudioed on 2019-05-12. Project shipment */
public interface MongoDocument<T> {

  Document toDoc();

  T fromDoc(Document doc);
}
