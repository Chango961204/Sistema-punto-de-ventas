/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Library.Codigos;
import Library.FormatDecimal;
import Library.Objectos;
import Library.Paginador;
import Library.Uploadimage;
import Models.Producto.ProductosModel;
import Models.Producto.TProductos;
import Models.Usuario.TUsuarios;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author ajpag
 */
public class ProductosVM extends Objectos {

    private List<JTextField> _textField;
    private List<JLabel> _label;
    private static TUsuarios _dataUsuario;
    private DefaultTableModel modelo1, modelo2, modelo3;
    private FormatDecimal _format;
    private int _reg_por_pagina = 10, _num_pagina = 1;
    private JTable _tableComprasTemporal, _table_Productos;
    private String _accion = "insert", _mony;
    public Codigos codigos;
    private JCheckBox _checkBoxCodigo;
    private JSpinner _spinnerPaginas;
    private JLabel labelImage;
    private JTabbedPane _tabbedPane;

    public int _seccion = 0;

    public ProductosVM(TUsuarios dataUsuario) {
        _dataUsuario = dataUsuario;
    }

    public ProductosVM(Object[] objectos,
            List<JTextField> textField,
            List<JLabel> label) {
        _textField = textField;
        _label = label;
        _checkBoxCodigo = (JCheckBox) objectos[1];
        _tableComprasTemporal = (JTable) objectos[2];
        _spinnerPaginas = (JSpinner) objectos[3];
        _table_Productos = (JTable) objectos[4];
        labelImage = (JLabel) objectos[5];
        _tabbedPane = (JTabbedPane) objectos[6];
        _format = new FormatDecimal();
        _mony = ConfigurationVM.Mony;
        codigos = new Codigos();
        restablecer();
    }
    private List<ProductosModel> listTemporalCompras;

    public void SearchCompras(String campo) {
        String[] titulos = {"IdCompra", "Descripcion", "Cantidad",
            "Precio", "Importe", "Credito", "Ticket", "Fecha", "Id"};
        modelo1 = new DefaultTableModel(null, titulos) {
            public Class<?> getColumnClass(int column) {
                if (column == 5) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };
        int inicio = (_num_pagina - 1) * _reg_por_pagina;
        if (campo.equals("")) {
            listTemporalCompras = Temporal_productos(_dataUsuario.getIdUsuario()).stream()
                    .collect(Collectors.toList());
        } else {
            listTemporalCompras = Temporal_productos(_dataUsuario.getIdUsuario()).stream()
                    .filter(p -> p.getDescripcion().startsWith(campo))
                    .collect(Collectors.toList());
        }
        var data = listTemporalCompras;
        var list = data.stream()
                .skip(inicio).limit(_reg_por_pagina)
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.forEach(item -> {
                Object[] registros = {
                    item.getIdCompra(),
                    item.getDescripcion(),
                    item.getCantidad(),
                    _mony + _format.decimal(item.getPrecio()),
                    _mony + _format.decimal(item.getImporte()),
                    item.getCredito(),
                    item.getTicket(),
                    item.getFecha(),
                    item.getId()
                };
                modelo1.addRow(registros);
            });
        }
        _tableComprasTemporal.setModel(modelo1);
        _tableComprasTemporal.setRowHeight(30);
        _tableComprasTemporal.getColumnModel().getColumn(0).setMaxWidth(0);
        _tableComprasTemporal.getColumnModel().getColumn(0).setMinWidth(0);
        _tableComprasTemporal.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tableComprasTemporal.getColumnModel().getColumn(8).setMaxWidth(0);
        _tableComprasTemporal.getColumnModel().getColumn(8).setMinWidth(0);
        _tableComprasTemporal.getColumnModel().getColumn(8).setPreferredWidth(0);
    }
    private int _idCompra = 0, cantidad, _idTemporal;
    private String precioCompra;
    private String producto;
    private int _idProducto = 0;

    public void dataCompra() {

        switch (_seccion) {
            case 0:
                _accion = "insert";
                var filas = _tableComprasTemporal.getSelectedRow();
                _idCompra = (Integer) modelo1.getValueAt(filas, 0);
                producto = (String) modelo1.getValueAt(filas, 1);
                cantidad = (Integer) modelo1.getValueAt(filas, 2);
                precioCompra = (String) modelo1.getValueAt(filas, 3);
                _idTemporal = (Integer) modelo1.getValueAt(filas, 8);
                _label.get(0).setText(producto);
                _label.get(1).setText(precioCompra);
                _textField.get(1).setText(producto);
                _textField.get(2).setText("");
                _label.get(4).setForeground(new Color(0, 153, 51));
                break;
            case 1:
                _accion = "update";
                var filas1 = _table_Productos.getSelectedRow();
                verificar = true;
                _idProducto = (Integer) modelo2.getValueAt(filas1, 0);
                _textField.get(0).setText((String) modelo2.getValueAt(filas1, 1));
                producto = (String) modelo2.getValueAt(filas1, 2);
                precioCompra = (String) modelo2.getValueAt(filas1, 3);
                var descuento = (String) modelo2.getValueAt(filas1, 4);
                _label.get(0).setText(producto);
                _label.get(1).setText(precioCompra);
                _textField.get(1).setText(producto);
                _textField.get(2).setText(precioCompra.replace(_mony, "").replace(",", ""));
                _textField.get(3).setText((String) modelo2.getValueAt(filas1, 5));
                _textField.get(4).setText((String) modelo2.getValueAt(filas1, 6));
                _textField.get(6).setText(descuento.replace("%", ""));

                _idCompra = (Integer) modelo2.getValueAt(filas1, 8);
                for (int i = 2; i < _label.size(); i++) {
                    _label.get(i).setForeground(new Color(0, 153, 51));
                }
                //ERROR
                uploadimage.byteImage(labelImage, (byte[]) modelo2.getValueAt(filas1, 9));
                break;
        }
        codigos.codigoBarra(_label.get(2), _textField.get(0).getText(), producto,
                _textField.get(2).getText());
    }
    private Double Precio;
    private boolean verificar = false;

    public void verificarPrecioVenta() {
        double compra;
        if (!_textField.get(2).getText().equals("") && !precioCompra.equals("")) {
            compra = _format.reconstruir(precioCompra.replace(_mony, ""));
            Precio = _format.reconstruir(_textField.get(2).getText());
            if (compra > Precio || compra == Precio) {
                switch (_seccion) {
                    case 0:
                        _label.get(5).setText("El precio debe ser mayor");
                        _label.get(5).setForeground(Color.red);
                        verificar = false;
                        break;
                    case 1:
                        var product = Compras().stream()
                                .filter(p -> p.getIdCompra() == _idCompra)
                                .collect(Collectors.toList());
                        if (0 < product.size()) {
                            var precio = product.get(0).getPrecio();
                            if (precio > Precio || precio == Precio) {
                                _label.get(5).setText("El precio debe ser mayor");
                                _label.get(5).setForeground(Color.red);
                                verificar = false;
                            } else {
                                verificar = true;
                            }
                        }
                        break;
                }
            } else {
                _label.get(5).setText("Precio venta");
                _label.get(5).setForeground(new Color(0, 153, 51));
                verificar = true;
            }
            _label.get(1).setText(_mony + _format.decimal(Precio));
        }
    }
    public int idProducto = 0;

    public boolean RegistrarProducto() throws SQLException {
        if (_idCompra == 0) {
            JOptionPane.showConfirmDialog(null,
                    "Seleccione un producto", "Producto",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            if (_checkBoxCodigo.isSelected()) {
                if (_textField.get(0).getText().equals("")) {
                    _label.get(3).setText("Ingrese el codigo");
                    _label.get(3).setForeground(Color.RED);
                    _textField.get(0).requestFocus();
                    return false;
                }
            }
            if (_textField.get(1).getText().equals("")) {
                _label.get(4).setText("Ingrese la descripcion");
                _label.get(4).setForeground(Color.RED);
                _textField.get(1).requestFocus();
            } else if (_textField.get(2).getText().equals("")) {
                _label.get(5).setText("Ingrese el precio de venta");
                _label.get(5).setForeground(Color.RED);
                _textField.get(2).requestFocus();
            } else if (_textField.get(3).getText().equals("")) {
                _label.get(6).setText("Ingrese el departamento");
                _label.get(6).setForeground(Color.RED);
                _textField.get(3).requestFocus();
            } else if (_textField.get(4).getText().equals("")) {
                _label.get(7).setText("Ingrese la categoria");
                _label.get(7).setForeground(Color.RED);
                _textField.get(4).requestFocus();
            } else if (codigos.Codigo == null) {
                _label.get(3).setText("El codigo ya esta registrado");
                _label.get(3).setForeground(Color.RED);
                _textField.get(0).requestFocus();
            } else if (verificar) {
                try {
                    List<TProductos> listProducto;
                    var qr = new QueryRunner(true);
                    getConn().setAutoCommit(false);
                    var producto = _textField.get(1).getText().replace(" ", "").toLowerCase();
                    var codigo = _textField.get(0).getText().equals("") ? codigos.Codigo
                            : _textField.get(0).getText();
                    if (0 < Productos().size()) {
                        listProducto = Productos().stream()
                                .filter(p -> p.getProducto().replace(" ", "").toLowerCase()
                                .equals(producto) && p.getPrecio().equals(Precio))
                                .collect(Collectors.toList());
                    } else {
                        listProducto = new ArrayList<TProductos>();
                    }
                    byte[] image = Uploadimage.getImageByte();
                    if (image == null) {
                        image = uploadimage.getTransFoto(labelImage);
                    }
                    switch (_accion) {
                        case "insert":
                            if (0 == listProducto.size()) {
                                String sqlProducto = "INSERT INTO tproductos(Codigo,Producto, Precio,Descuento,"
                                        + " Departamento,Categoria,Fecha,IdCompra,Imagen) VALUES(?,?,?,?,?,?,?,?,?)";
                                Object[] dataProducto = {
                                    codigo,
                                    _textField.get(1).getText(),
                                    Precio,
                                    0.0,
                                    _textField.get(3).getText(),
                                    _textField.get(4).getText(),
                                    new Date(),
                                    _idCompra,
                                    image
                                };
                                var data = (List<? extends Number>) qr.insert(getConn(), sqlProducto, new ColumnListHandler(), dataProducto);
                                idProducto = data.get(0).intValue();
                            } else {
                                idProducto = listProducto.get(0).getIdProducto();
                                String sqlProducto = "UPDATE tproductos SET IdCompra = ? WHERE IdProducto =" + idProducto;
                                Object[] dataProducto = {
                                    _idCompra,};
                                qr.update(getConn(), sqlProducto, dataProducto);
                            }
                            var bodega = Bodega().stream()
                                    .filter(p -> p.getIdProducto() == idProducto)
                                    .collect(Collectors.toList());
                            if (0 < bodega.size()) {
                                var cant = cantidad + bodega.get(0).getExistencia();
                                String sqlBodega = "UPDATE tbodega SET IdProducto = ?,Existencia = ?,"
                                        + "Fecha = ? WHERE Id =" + bodega.get(0).getId();
                                Object[] dataBodega = {
                                    bodega.get(0).getIdProducto(),
                                    cant,
                                    new Date()
                                };
                                qr.update(getConn(), sqlBodega, dataBodega);
                            } else {
                                String sqlProducto = "INSERT INTO tbodega(IdProducto,Existencia, Fecha)"
                                        + " VALUES(?,?,?)";
                                Object[] dataProducto = {
                                    idProducto,
                                    cantidad,
                                    new Date()
                                };
                                qr.insert(getConn(), sqlProducto, new ColumnListHandler(), dataProducto);
                            }
//                            try {
//                                var sql = "DELETE FROM tcompras_temporal WHERE Id LIKE ?";
//                                qr.update(getConn(), sql, "%" + _idTemporal + "%");
//                            } catch (Exception e) {
//                            }
                            break;
                        case "update":
                            var sqlProducto1 = "UPDATE tproductos SET Codigo = ?,"
                                    + "Producto = ?,Precio = ?,Descuento = ?,Departamento = ?"
                                    + ",Categoria = ?,Imagen = ? WHERE IdProducto =" + _idProducto;
                            var descuento = _textField.get(6).getText().equals("")
                                    ? 0.0 : _format.reconstruir(_textField.get(6).getText());
                            Object[] dataProducto1 = {
                                _textField.get(0).getText(),
                                _textField.get(1).getText(),
                                _textField.get(2).getText(),
                                descuento,
                                _textField.get(3).getText(),
                                _textField.get(4).getText(),
                                image
                            };
                            qr.update(getConn(), sqlProducto1, dataProducto1);
                            break;
                    }
                    getConn().commit();
                    restablecer();
                } catch (Exception e) {
                    getConn().rollback();
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        return false;
    }
    private List<TProductos> _listProductos;

    public void SearchProducto(String campo) {
        String[] titulos = {"IdProducto", "Codigo", "Producto",
            "Precio", "Descuento", "Departamento", "Categoria",
            "Fecha", "IdCompra", "Imagen"};
        modelo2 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            _listProductos = Productos();
        } else {
            _listProductos = Productos().stream()
                    .filter(p -> p.getProducto().startsWith(campo)
                    || p.getCodigo().startsWith(campo))
                    .collect(Collectors.toList());
        }
        var inicio = (_num_pagina - 1) * _reg_por_pagina;
        var data = _listProductos;
        var list = data.stream()
                .skip(inicio).limit(_reg_por_pagina)
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.forEach(item -> {
                Object[] registros = {
                    item.getIdProducto(),
                    item.getCodigo(),
                    item.getProducto(),
                    _mony + _format.decimal(item.getPrecio()),
                    "%" + _format.decimal(item.getDescuento()),
                    item.getDepartamento(),
                    item.getCategoria(),
                    item.getFecha(),
                    item.getIdCompra(),
                    item.getImagen()
                };
                modelo2.addRow(registros);
            });

        }
        _table_Productos.setModel(modelo2);
        _table_Productos.setRowHeight(30);
        _table_Productos.getColumnModel().getColumn(0).setMaxWidth(0);
        _table_Productos.getColumnModel().getColumn(0).setMinWidth(0);
        _table_Productos.getColumnModel().getColumn(0).setPreferredWidth(0);
        _table_Productos.getColumnModel().getColumn(8).setMaxWidth(0);
        _table_Productos.getColumnModel().getColumn(8).setMinWidth(0);
        _table_Productos.getColumnModel().getColumn(8).setPreferredWidth(0);
        _table_Productos.getColumnModel().getColumn(9).setMaxWidth(0);
        _table_Productos.getColumnModel().getColumn(9).setMinWidth(0);
        _table_Productos.getColumnModel().getColumn(9).setPreferredWidth(0);
    }

    public void restablecer() {
        _seccion = _tabbedPane.getSelectedIndex();
        _accion = "insert";
        for (int i = 0; i < _textField.size(); i++) {
            _textField.get(i).setText("");

        }
        for (int i = 2; i < _label.size(); i++) {
            _label.get(i).setForeground(new Color(102, 102, 102));
        }
        _textField.get(0).setEnabled(false);
        _checkBoxCodigo.setSelected(false);
        _checkBoxCodigo.setForeground(new Color(102, 102, 102));
        _label.get(0).setText("Descripción");
        _label.get(1).setText(_mony + "0.00");
        _textField.get(6).setEnabled(_seccion == 0 ? false : true);
        SearchCompras(_textField.get(5).getText());
        codigos.codigoBarra(_label.get(2), "0000000000", _textField.get(1).getText(),
                _textField.get(2).getText());
        var model = new SpinnerNumberModel(
                10.0, // Dato visualizado al inicio en el spinner 
                1.0, // Límite inferior 
                100.0, // Límite superior 
                1.0 // incremento-decremento 
        );
        labelImage.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("Resources/logo-google_1.png")));
        _spinnerPaginas.setModel(model);
        if (!listTemporalCompras.isEmpty()) {
            _paginadorCompras = new Paginador<>(listTemporalCompras,
                    _label.get(8), _reg_por_pagina);
        }
        SearchProducto(_textField.get(5).getText());
    }
    private Paginador<ProductosModel> _paginadorCompras;
    private Paginador<TProductos> _paginadorProductos;

    public void Paginador(String metodo) {
        switch (metodo) {
            case "Primero":
                switch (_seccion) {
                    case 0:
                        if (!listTemporalCompras.isEmpty()) {
                            _num_pagina = _paginadorCompras.primero();
                        }
                        break;
                    case 1:
                        if (!_listProductos.isEmpty()) {
                            _num_pagina = _paginadorProductos.primero();
                        }
                        break;
                }
                break;
            case "Anterior":
                switch (_seccion) {
                    case 0:
                        if (!listTemporalCompras.isEmpty()) {
                            _num_pagina = _paginadorCompras.anterior();
                        }
                        break;
                    case 1:
                        if (!_listProductos.isEmpty()) {
                            _num_pagina = _paginadorProductos.anterior();
                        }
                        break;
                }
                break;
            case "Siguiente":
                switch (_seccion) {
                    case 0:
                        if (!listTemporalCompras.isEmpty()) {
                            _num_pagina = _paginadorCompras.siguiente();
                        }
                        break;
                    case 1:
                        if (!_listProductos.isEmpty()) {
                            _num_pagina = _paginadorProductos.siguiente();
                        }
                        break;
                }
                break;
            case "Ultimo":
                switch (_seccion) {
                    case 0:
                        if (!listTemporalCompras.isEmpty()) {
                            _num_pagina = _paginadorCompras.ultimo();
                        }
                        break;
                    case 1:
                        if (!_listProductos.isEmpty()) {
                            _num_pagina = _paginadorProductos.ultimo();
                        }
                        break;
                }
                break;
        }
        switch (_seccion) {
            case 0:
                SearchCompras(_textField.get(5).getText());
                break;
            case 1:
                SearchProducto(_textField.get(5).getText());
                break;
        }
    }

    public void Registro_Paginas() {
        _num_pagina = 1;
        var value = (Number) _spinnerPaginas.getValue();
        _textField.get(6).setEnabled(_seccion == 0 ? false : true);
        _reg_por_pagina = value.intValue();
        switch (_seccion) {
            case 0:
                if (!listTemporalCompras.isEmpty()) {
                    _paginadorCompras = new Paginador<>(listTemporalCompras,
                            _label.get(8), _reg_por_pagina);
                    SearchCompras(_textField.get(5).getText());
                }
                break;
            case 1:
                if (!_listProductos.isEmpty()) {
                    _paginadorProductos = new Paginador<>(_listProductos,
                            _label.get(8), _reg_por_pagina);
                    SearchProducto(_textField.get(5).getText());
                }
                break;
        }
    }
}
