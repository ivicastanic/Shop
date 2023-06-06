module com.shop.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.sql.rowset;


    opens com.shop to javafx.base,org.hibernate.orm.core,javafx.fxml;
    opens com.shop.employee to javafx.base,org.hibernate.orm.core;
    opens com.shop.employee.privilege to javafx.base,org.hibernate.orm.core;
    opens com.shop.employee.privilege.service to javafx.base,org.hibernate.orm.core;
    opens com.shop.service to javafx.base,org.hibernate.orm.core,jakarta.persistence;
    opens com.shop.product to javafx.base,org.hibernate.orm.core;
    opens com.shop.product.service to javafx.base, org.hibernate.orm.core;
    opens com.shop.order to javafx.base,org.hibernate.orm.core;
    opens com.shop.order.order_item to javafx.base,org.hibernate.orm.core;
    opens com.shop.order.order_status to javafx.base,org.hibernate.orm.core;
    opens com.shop.country.town to javafx.base,org.hibernate.orm.core;
    opens com.shop.country to javafx.base,org.hibernate.orm.core;
    opens com.shop.country.town.address to javafx.base,org.hibernate.orm.core;
    opens com.shop.customer to javafx.base,org.hibernate.orm.core;



    exports com.shop;
    exports com.shop.UI;
    exports com.shop.UI.paneli;
    exports com.shop.employee;
}