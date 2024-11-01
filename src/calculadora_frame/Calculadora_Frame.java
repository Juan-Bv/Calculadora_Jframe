package calculadora_frame;

import javax.swing.*; // Importo las clases necesarias para construir la interfaz gráfica
import java.awt.*; // Importo clases para diseño y disposición de componentes
import java.awt.event.ActionEvent; // Importo esta clase para manejar eventos de los botones
import java.awt.event.ActionListener; // Importo la interfaz para escuchar eventos de acción de los botones
import java.util.Stack; // Importo la clase Stack para manejar los operadores y valores en la evaluación de expresiones

// Profesor, aquí defino la clase principal de la calculadora avanzada, donde configuro la interfaz gráfica y la funcionalidad de cada botón.
public class Calculadora_Frame extends JFrame implements ActionListener {

    private JTextField pantalla; // Declaro la pantalla para mostrar la entrada y el resultado
    private StringBuilder entradaActual; // Declaro un StringBuilder para almacenar lo que el usuario escribe

    // Profesor aquí creo este constructor, ya que se encarga de inicializar la ventana de la calculadora, los botones y sus configuraciones.
    public Calculadora_Frame() {
        setTitle("Calculadora Avanzada"); // Establezco el título de la ventana
        setSize(400, 500); // Configuro el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configuro la operación de cierre de la ventana
        setLayout(new BorderLayout()); // Uso un BorderLayout para organizar los componentes de la interfaz

        /* ¿Para que utilizo BorderLayout? lo utilizo, ya que investigue y ví que ayuda a  dividir el contenedor en cinco áreas: NORTH, SOUTH, EAST, WEST y CENTER.
           Esto permite colocar componentes en posiciones fijas, y ajusta su tamaño según el espacio disponible.
           Aquí lo utilizo para poner la pantalla de la calculadora en la parte superior y el panel de botones en el centro. */
        
        entradaActual = new StringBuilder(); // Inicializo la entrada del usuario como un StringBuilder vacío

        // Configuro la pantalla para mostrar números y resultados
        pantalla = new JTextField();
        pantalla.setEditable(false); // Evito que el usuario pueda escribir directamente en la pantalla
        pantalla.setFont(new Font("Arial", Font.BOLD, 32)); // Configuro el tamaño y estilo de fuente de la pantalla
        pantalla.setHorizontalAlignment(SwingConstants.RIGHT); // Alineo el texto a la derecha para una mejor visualización
        pantalla.setBackground(new Color(240, 248, 255)); // Le doy un color de fondo a la pantalla
        pantalla.setForeground(Color.BLACK); // Configuro el color del texto en negro
        add(pantalla, BorderLayout.NORTH); // Coloco la pantalla en la parte superior de la interfaz

        /* Profesor,  utilizo BorderLayout.NORTH , ya que al usarlo, el componente 'pantalla' se mantiene fijo en la parte superior de la ventana.
           Esto significa que la pantalla no se verá afectada por los cambios de tamaño del panel de botones ni del contenido del centro. */
       
        // Aquí defino un panel donde colocaré los botones de la calculadora
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 4, 10, 10)); // Organizo los botones en un grid de 5 filas x 4 columnas con un espacio entre ellos
        panelBotones.setBackground(new Color(255, 235, 205)); // Asigno un color de fondo para los botones

        // Aquí profesor, defino las etiquetas de los botones, con números y operadores básicos.
        String[] botones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "(", ")", "←"
        };

        // Recorro cada etiqueta de los botones, creo el botón, le aplico formato y lo añado al panel
        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.PLAIN, 24)); // Configuro el tamaño de la fuente de cada botón
            boton.setBackground(new Color(210, 180, 140)); // Le asigno un color de fondo
            boton.setForeground(Color.BLACK); // Configuro el color del texto en negro
            boton.setFocusPainted(false); // Desactivo el borde de enfoque de los botones para mejorar la apariencia
            boton.addActionListener(this); // Asigno este objeto como el listener para manejar los eventos de cada botón
            panelBotones.add(boton); // Añado el botón al panel
        }

        add(panelBotones, BorderLayout.CENTER); // Coloco el panel de botones en el centro de la interfaz

        /* Profesor, utilizo BorderLayout.CENTER, ya que me ayuda que el componente 'panelBotones' ocupe el espacio central.
           Esta posición se expandirá automáticamente, adaptándose al tamaño de la ventana, permitiendo
           que el panel de botones llene el espacio central disponible. */
        
        getContentPane().setBackground(new Color(255, 222, 173)); // Configuro el color de fondo de la interfaz
    }

    // Profesor, use y cree este método, ya que  escucha y maneja las acciones de cada botón cuando es presionado.
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand(); // Obtengo el texto del botón que fue presionado

        switch (comando) {
            case "=":
                calcularResultado(); // Llamo a calcular el resultado al presionar el botón "="
                break;
            case "C":
                limpiarPantalla(); // Limpio la pantalla cuando el usuario presiona "C"
                break;
            case "←":
                borrarUltimoCaracter(); // Borro el último carácter cuando se presiona "←"
                break;
            default:
                entradaActual.append(comando); // Agrego el texto del botón presionado a la entrada actual
                pantalla.setText(entradaActual.toString()); // Actualizo la pantalla para mostrar la entrada actual
                break;
        }
    }

    // En este método profesor, se evalúa la expresión ingresada y muestra el resultado en la pantalla
    private void calcularResultado() {
        try {
            double resultado = evaluarExpresion(entradaActual.toString()); // Llamo a evaluar la expresión
            pantalla.setText(String.valueOf(resultado)); // Muestro el resultado en la pantalla
            entradaActual = new StringBuilder(String.valueOf(resultado)); // Actualizo la entrada actual con el resultado
        } catch (Exception e) {
            pantalla.setText("Error"); // Muestro "Error" si ocurre un problema en la evaluación
            entradaActual = new StringBuilder(); // Limpio la entrada actual para evitar errores futuros
        }
    }

    // Creo un método que reinicia la entrada y la pantalla, dejándolas en blanco
    private void limpiarPantalla() {
        entradaActual = new StringBuilder(); // Limpio la entrada actual
        pantalla.setText(""); // Limpio la pantalla de la calculadora
    }

    // Creo un método borra el último carácter de la entrada si hay algo que borrar
    private void borrarUltimoCaracter() {
        if (entradaActual.length() > 0) { // Verifico que la entrada no esté vacía
            entradaActual.deleteCharAt(entradaActual.length() - 1); // Elimino el último carácter
            pantalla.setText(entradaActual.toString()); // Actualizo la pantalla con la nueva entrada
        }
    }

    // Aquí profesor, uso el método para evaluar una expresión matemática, utilizando pilas para gestionar operadores y valores
    private double evaluarExpresion(String expresion) {
        Stack<Double> valores = new Stack<>(); // Aquí la pila almacena valores numéricos
        Stack<Character> operadores = new Stack<>(); // Aquí la pila almacena operadores

        /* Para que utilice la pila o stack profesor? primero que todo la pila o stack es una estructura de datos que sigue el principio LIFO (Last In, First Out),
         donde el último elemento en ser agregado es el primero en ser retirado. En el código de/*
         */
        
        /* por ejemplo, para la calculadora, utilice las pilas o stack para manejar operadores y valores durante la evaluación
        de expresiones matemáticas. Al agregar números y operadores a las pilas, la calculadora
         puede evaluar la expresión de manera ordenada, asegurando que se respete lo que es la precedencia
         de los operadores. Por lo que profesor, Esto permite realizar cálculos o mejor dicho 
        situaciones como la división por cero, colocando excepciones cuando es necesario.*/
 
        // Aquí Recorrí cada carácter de la expresión
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (Character.isDigit(c) || c == '.') { // Detecto dígitos y el punto decimal para construir números
                double num = 0;
                boolean decimal = false; // Indico si se está procesando un número decimal
                double factor = 1; // Creo un factor para manejar decimales

                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    if (expresion.charAt(i) == '.') {
                        decimal = true; // Indico que estamos en la parte decimal
                    } else {
                        if (decimal) {
                            factor /= 10; // Ajusto el factor para manejar los decimales
                            num += (expresion.charAt(i) - '0') * factor; // Agrego el dígito decimal
                        } else {
                            num = num * 10 + (expresion.charAt(i) - '0'); // Construyo el número entero
                        }
                    }
                    i++;
                }
                valores.push(num); // Agrego el número a la pila de valores
                i--; // Ajusto el índice para seguir el bucle adecuadamente
            } else if (c == '(') {
                operadores.push(c); // Agrego el paréntesis de apertura a la pila de operadores
            } else if (c == ')') {
                while (operadores.peek() != '(') { // Resuelvo operaciones dentro de paréntesis
                    valores.push(aplicarOperador(operadores.pop(), valores.pop(), valores.pop())); // Calculo el resultado
                }
                operadores.pop(); // Elimino el paréntesis de apertura
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operadores.isEmpty() && precedencia(c, operadores.peek())) {
                    valores.push(aplicarOperador(operadores.pop(), valores.pop(), valores.pop())); // Aplico operaciones según la precedencia
                }
                operadores.push(c); // Agrego el operador a la pila de operadores
            }
        }

        while (!operadores.isEmpty()) { // Realizo las operaciones restantes
            valores.push(aplicarOperador(operadores.pop(), valores.pop(), valores.pop()));
        }
        return valores.pop(); // Devuelvo el resultado final de la expresión
    }

    // Este método compara la precedencia entre dos operadores
    private boolean precedencia(char operador1, char operador2) {
        return (operador2 == '*' || operador2 == '/') && (operador1 == '+' || operador1 == '-');
    }

    // Método que aplica un operador a dos valores y retorna el resultado
    private double aplicarOperador(char operador, double b, double a) {
        switch (operador) {
            case '+':
                return a + b; // Retorno la suma
            case '-':
                return a - b; // Retorno la resta
            case '*':
                return a * b; // Retorno la multiplicación
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("División por cero"); // Aquí profesor, creo un manejo de error por división entre cero
                }
                return a / b; // Retorno la división
        }
        return 0; // Retorno cero si no hay coincidencia de operador
    }

    // Y ya por ultimo profesor, creo el método principal que ejecuta la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora_Frame calculadora = new Calculadora_Frame(); // Creo una instancia de la calculadora
            calculadora.setVisible(true); // y hago visible la ventana
        });
    }
}

