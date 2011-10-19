/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swingtestapplet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Павел
 */
public class SwingTestApplet extends JApplet
{
    public static final int cc_InitWidth=640;
    public static final int cc_InitHeight=480;
    public static final String cc_Title="Отрисовка производных";
    public static final String[] cc_functions=new String[]{"a","a*x","x^a","e^a*x","sin(x*a)", "cos(x*a)", "tan(x*a)", "ln_a(x)"};
    
    String cf_choosedFunction;
    double cf_multiplierX;
    double cf_multiplierY;
    double cf_param, cf_tangent_x, cf_tangent_f, cf_tangent_p;
    
    //Label f_choosedFunction;
    //Label f_chooseFunction;
    JComboBox f_function;
    JButton f_select;
    JTextField f_multiplierX;
    JTextField f_multiplierY;
    JTextField f_param;
    JTextField f_tangent;
    JPanel f_selectPanel1,f_selectPanel2;
    cGraphicPanel f_graph;
    
    public SwingTestApplet()
    {
        //super(cc_Title);
        cf_choosedFunction="a";
        cf_multiplierX=16;
        cf_multiplierY=16;
        cf_param=1;
        cf_tangent_x=1;
        setSize(cc_InitWidth, cc_InitHeight);
        //setResizable(false);
        f_selectPanel1=new JPanel(new FlowLayout(FlowLayout.CENTER));
        f_selectPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        f_graph=new cGraphicPanel();
        f_function=new JComboBox();
        for (String _str : cc_functions)
        {
            f_function.addItem(_str);
        }        
        //setLayout(new GridLayout(0, 1));
        setLayout(new BorderLayout());
        //f_choosedFunction=new Label("Текущая функция: ", Label.CENTER);
        //f_chooseFunction=new Label("Функция: ", Label.CENTER);
        f_select=new JButton("Отрисовать производную");
        f_select.addActionListener(new ActionListenerImpl());
        f_selectPanel1.add(new JLabel("Функция: ", JLabel.CENTER));
        f_selectPanel1.add(f_function);
        f_selectPanel1.add(f_select);
        f_selectPanel1.add(new JLabel("Точка касания:"));
        f_tangent=new JTextField("1", 8);
        f_selectPanel1.add(f_tangent);
        f_selectPanel2.add(new JLabel("Масштабный коэф. по X:"));
        f_multiplierX=new JTextField("16", 8);
        f_selectPanel2.add(f_multiplierX);
        f_selectPanel2.add(new JLabel("Масштабный коэф. по Y:"));
        f_multiplierY=new JTextField("16", 8);
        f_selectPanel2.add(f_multiplierY);
        f_selectPanel2.add(new JLabel("a="));
        f_param=new JTextField("1", 8);
        f_selectPanel2.add(f_param);
        add("Center",f_graph);
        add("North",f_selectPanel1);
        add("South",f_selectPanel2);
        //add(f_choosedFunction);
        //addWindowListener(new cWindowAdapter());
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SwingTestApplet _test=new SwingTestApplet();
        _test.setVisible(true);
    }
    
//    private class cWindowAdapter extends WindowAdapter
//    {
//        @Override
//        public void windowClosing(WindowEvent we)
//        {
//            setVisible(false);
//            dispose();
//            System.exit(0);
//        }
//    }

    private class ActionListenerImpl implements ActionListener
    {
        public ActionListenerImpl() {
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            cf_choosedFunction=(String)f_function.getSelectedItem();
            //f_choosedFunction.setText("Текущая функция: "+cf_choosedFunction);
            String _temp;
            _temp=f_multiplierX.getText();
            cf_multiplierX=Double.parseDouble(_temp);
            _temp=f_multiplierY.getText();
            cf_multiplierY=Double.parseDouble(_temp);
            _temp=f_param.getText();
            cf_param=Double.parseDouble(_temp);
            _temp=f_tangent.getText();
            cf_tangent_x=Double.parseDouble(_temp);
            if (cf_choosedFunction.equals("a"))
            {
                cf_tangent_f=cf_param;
            } else
            if (cf_choosedFunction.equals("a*x"))
            {
                cf_tangent_f=cf_param*cf_tangent_x;
                cf_tangent_p=cf_param;
            } else
            if (cf_choosedFunction.equals("x^a"))
            {
                cf_tangent_f=Math.pow(cf_tangent_x, cf_param);
                cf_tangent_p=cf_param*Math.pow(cf_tangent_x, cf_param-1);
            } else
            if (cf_choosedFunction.equals("e^a*x"))
            {
                cf_tangent_f=Math.exp(cf_param*cf_tangent_x);
                cf_tangent_p=cf_param*Math.exp(cf_param*cf_tangent_x);
            } else
            if (cf_choosedFunction.equals("sin(x*a)"))
            {
                cf_tangent_f=Math.sin(cf_param*cf_tangent_x);
                cf_tangent_p=cf_param*Math.cos(cf_param*cf_tangent_x);
            } else
            if (cf_choosedFunction.equals("cos(x*a)"))
            {
                cf_tangent_f=Math.cos(cf_param*cf_tangent_x);
                cf_tangent_p=-cf_param*Math.sin(cf_param*cf_tangent_x);
            } else
            if (cf_choosedFunction.equals("tan(x*a)"))
            {
                cf_tangent_f=Math.tan(cf_param*cf_tangent_x);
                cf_tangent_p=cf_param*(Math.tan(cf_param*cf_tangent_x)*Math.tan(cf_param*cf_tangent_x)+1);
            } else
            if (cf_choosedFunction.equals("ln_a(x)"))
            {
                cf_tangent_f=Math.log(cf_tangent_x)/Math.log(cf_param);
                cf_tangent_p=1/(cf_tangent_x*Math.log(cf_param));
            }
            repaint();
        }
    }
    
    private class cGraphicPanel extends JPanel
    {

        public cGraphicPanel()
        {
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D _graph=(Graphics2D)g;
            Dimension _dimension=getSize();
            int _halfW=_dimension.width>>1;
            int _halfH=_dimension.height>>1;
            _graph.clearRect(0, 0, _dimension.width, _dimension.height);
            _graph.setColor(Color.BLACK);
            _graph.drawLine(0, _halfH, _dimension.width, _halfH);
            _graph.drawLine(_halfW, 0, _halfW, _dimension.height);
            int _y_f[]=new int[_dimension.width];
            int _y_p[]=new int[_dimension.width];
            int _y_k[]=new int[_dimension.width];
            int _x_i[]=new int[_dimension.width];
            for (int i=0; i<_dimension.width; i++)
            {
                _x_i[i]=i;
                double _x=((i-_halfW)/cf_multiplierX);
                double _val_f=0,_val_p=0, _val_t=0;
                if (cf_choosedFunction.equals("a"))
                {
                    _val_f=cf_param;
                    _val_p=0;
                } else
                if (cf_choosedFunction.equals("a*x"))
                {
                    _val_f=cf_param*_x;
                    _val_p=cf_param;
                } else
                if (cf_choosedFunction.equals("x^a"))
                {
                    _val_f=Math.pow(_x, cf_param);
                    _val_p=cf_param*Math.pow(_x, cf_param-1);
                } else
                if (cf_choosedFunction.equals("e^a*x"))
                {
                    _val_f=Math.exp(cf_param*_x);
                    _val_p=cf_param*Math.exp(cf_param*_x);
                } else
                if (cf_choosedFunction.equals("sin(x*a)"))
                {
                    _val_f=Math.sin(cf_param*_x);
                    _val_p=cf_param*Math.cos(cf_param*_x);
                } else
                if (cf_choosedFunction.equals("cos(x*a)"))
                {
                    _val_f=Math.cos(cf_param*_x);
                    _val_p=-cf_param*Math.sin(cf_param*_x);
                } else
                if (cf_choosedFunction.equals("tan(x*a)"))
                {
                    _val_f=Math.tan(cf_param*_x);
                    _val_p=cf_param*(Math.tan(cf_param*_x)*Math.tan(cf_param*_x)+1);
                } else
                if (cf_choosedFunction.equals("ln_a(x)"))
                {
                    _val_f=Math.log(_x)/Math.log(cf_param);
                    _val_p=1/(_x*Math.log(cf_param));
                }
                _val_t=cf_tangent_f+cf_tangent_p*(_x-cf_tangent_x);
                _y_f[i]=_halfH-(int)(_val_f*cf_multiplierY);
                _y_p[i]=_halfH-(int)(_val_p*cf_multiplierY);
                _y_k[i]=_halfH-(int)(_val_t*cf_multiplierY);
            }
            _graph.setColor(Color.BLUE);
            _graph.drawPolyline(_x_i, _y_f, _dimension.width);
            _graph.setColor(Color.RED);
            _graph.drawPolyline(_x_i, _y_p, _dimension.width);
            _graph.setColor(Color.GREEN);
            _graph.drawPolyline(_x_i, _y_k, _dimension.width);
        }
    }
}
