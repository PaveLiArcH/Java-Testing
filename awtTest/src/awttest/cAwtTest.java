/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package awttest;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Pavel
 */
public class cAwtTest extends Frame
{
    public static final int cc_InitWidth=640;
    public static final int cc_InitHeight=480;
    public static final String cc_Title="Отрисовка производных";
    public static final String[] cc_functions=new String[]{"a","a*x","x^a","e^a*x","sin(x*a)", "cos(x*a)", "tan(x*a)", "ln(a*x)"};
    
    String cf_choosedFunction;
    double cf_multiplierX;
    double cf_multiplierY;
    double cf_param;
    
    //Label f_choosedFunction;
    //Label f_chooseFunction;
    Choice f_function;
    Button f_select;
    TextField f_multiplierX;
    TextField f_multiplierY;
    TextField f_param;
    Panel f_selectPanel1,f_selectPanel2;
    Panel f_graph;
    
    public cAwtTest()
    {
        super(cc_Title);
        cf_choosedFunction="c";
        cf_multiplierX=16;
        cf_multiplierY=16;
        cf_param=1;
        setSize(cc_InitWidth, cc_InitHeight);
        //setResizable(false);
        f_selectPanel1=new Panel(new FlowLayout(FlowLayout.CENTER));
        f_selectPanel2=new Panel(new FlowLayout(FlowLayout.CENTER));
        f_graph=new Panel(new FlowLayout(FlowLayout.CENTER));
        f_function=new Choice();
        for (String _str : cc_functions)
        {
            f_function.add(_str);
        }        
        //setLayout(new GridLayout(0, 1));
        setLayout(new BorderLayout());
        //f_choosedFunction=new Label("Текущая функция: ", Label.CENTER);
        //f_chooseFunction=new Label("Функция: ", Label.CENTER);
        f_select=new Button("Отрисовать производную");
        f_select.addActionListener(new ActionListenerImpl());
        f_selectPanel1.add(new Label("Функция: ", Label.CENTER));
        f_selectPanel1.add(f_function);
        f_selectPanel1.add(f_select);
        f_selectPanel2.add(new Label("Масштабный коэф. по X:"));
        f_multiplierX=new TextField("16", 8);
        f_selectPanel2.add(f_multiplierX);
        f_selectPanel2.add(new Label("Масштабный коэф. по Y:"));
        f_multiplierY=new TextField("16", 8);
        f_selectPanel2.add(f_multiplierY);
        f_selectPanel2.add(new Label("a="));
        f_param=new TextField("1", 8);
        f_selectPanel2.add(f_param);
        add("Center",f_graph);
        add("North",f_selectPanel1);
        add("South",f_selectPanel2);
        //add(f_choosedFunction);
        addWindowListener(new cWindowAdapter());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D _graph=(Graphics2D)f_graph.getGraphics();
        Dimension _dimension=f_graph.getSize();
        int _halfW=_dimension.width>>1;
        int _halfH=_dimension.height>>1;
        _graph.clearRect(0, 0, _dimension.width, _dimension.height);
        _graph.setColor(Color.BLACK);
        _graph.drawLine(0, _halfH, _dimension.width, _halfH);
        _graph.drawLine(_halfW, 0, _halfW, _dimension.height);
        int _y[]=new int[_dimension.width];
        int _x_i[]=new int[_dimension.width];
        for (int i=0; i<_dimension.width; i++)
        {
            _x_i[i]=i;
            double _x=((i-_halfW)/cf_multiplierX);
            double _val=0;
            if (cf_choosedFunction.equals("a"))
            {
                _val=0;
            } else
            if (cf_choosedFunction.equals("a*x"))
            {
                _val=cf_param;
            } else
            if (cf_choosedFunction.equals("x^a"))
            {
                _val=cf_param*Math.pow(_x, cf_param-1);
            } else
            if (cf_choosedFunction.equals("e^a*x"))
            {
                _val=cf_param*Math.exp(cf_param*_x);
            } else
            if (cf_choosedFunction.equals("sin(x*a)"))
            {
                _val=cf_param*Math.cos(cf_param*_x);
            } else
            if (cf_choosedFunction.equals("cos(x*a)"))
            {
                _val=-cf_param*Math.sin(cf_param*_x);
            } else
            if (cf_choosedFunction.equals("tan(x*a)"))
            {
                _val=cf_param*(Math.tan(cf_param*_x)*Math.tan(cf_param*_x)+1);
            } else
            if (cf_choosedFunction.equals("ln(a*x)"))
            {
                _val=1/(_x*Math.log(cf_param));
            }
            _y[i]=_halfH-(int)(_val*cf_multiplierY);
        }
        _graph.setColor(Color.BLUE);
        _graph.drawPolyline(_x_i, _y, _dimension.width);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        cAwtTest _test=new cAwtTest();
        _test.setVisible(true);
    }
    
    private class cWindowAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent we)
        {
            setVisible(false);
            dispose();
            System.exit(0);
        }
    }

    private class ActionListenerImpl implements ActionListener
    {
        public ActionListenerImpl() {
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            cf_choosedFunction=f_function.getSelectedItem();
            //f_choosedFunction.setText("Текущая функция: "+cf_choosedFunction);
            String _temp;
            _temp=f_multiplierX.getText();
            cf_multiplierX=Double.parseDouble(_temp);
            _temp=f_multiplierY.getText();
            cf_multiplierY=Double.parseDouble(_temp);
            _temp=f_param.getText();
            cf_param=Double.parseDouble(_temp);
            repaint();
        }
    }
}