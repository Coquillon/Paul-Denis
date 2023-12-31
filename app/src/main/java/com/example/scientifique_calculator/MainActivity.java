package com.example.scientifique_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    //Initialiser nos boutons
    Button btn_ac, btn_p1, btn_p2, btn_del, btn_sin, btn_cos, btn_tan, btn_ln, btn_log, btn_fact, btn_pow, btn_racine, btn_inv, btn_div, btn_mult, btn_moins, btn_plus, btn_pow_x, btn_egale;
    Button btn_7, btn_8, btn_9, btn_4, btn_5, btn_6, btn_3, btn_2, btn_1, btn_zero, btn_point, btn_rd_deg;
    //TextView
    static private TextView ecran_2;  static private EditText ecran_main;
    private String buton_text; private int cursorPosition;
    DecimalFormat decimalFormat = new DecimalFormat("#.######"); DecimalFormat scientificFormat = new DecimalFormat("0.###E0");
    private double baseForPowX = 0.0; private boolean waitingForExponent = false;
    // Initialisez l'état actuel (radians)
    private static boolean isRadiansMode = true;
    private BigInteger facto = BigInteger.ONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //====================================
            btn_ac = findViewById(R.id.btn_ac); btn_p1 = findViewById(R.id.btn_p1); btn_p2 = findViewById(R.id.btn_p2); btn_del = findViewById(R.id.btn_del);
            //----------------------------------
            btn_sin = findViewById(R.id.btn_sin); btn_cos = findViewById(R.id.btn_cos); btn_tan = findViewById(R.id.btn_tan); btn_ln = findViewById(R.id.btn_ln); btn_log = findViewById(R.id.btn_log);
            //-----------------------------------
            btn_fact = findViewById(R.id.btn_fact); btn_pow = findViewById(R.id.btn_pow); btn_pow_x = findViewById(R.id.btn_pow_x); btn_racine = findViewById(R.id.btn_racine);
            btn_inv = findViewById(R.id.btn_inv); btn_div = findViewById(R.id.btn_div);
            //------------------------------------
            btn_mult = findViewById(R.id.btn_mult); btn_moins = findViewById(R.id.btn_moins); btn_plus = findViewById(R.id.btn_plus); btn_egale = findViewById(R.id.btn_egale);

            //=====================================
            btn_7 = findViewById(R.id.btn_7); btn_8 = findViewById(R.id.btn_8); btn_9 = findViewById(R.id.btn_9); btn_4 = findViewById(R.id.btn_4); btn_5 = findViewById(R.id.btn_5);
            //-----------------------------------
            btn_6 = findViewById(R.id.btn_6); btn_3 = findViewById(R.id.btn_3); btn_2 = findViewById(R.id.btn_2); btn_1 = findViewById(R.id.btn_1);
            btn_zero = findViewById(R.id.btn_zero); btn_point = findViewById(R.id.btn_point); btn_rd_deg = findViewById(R.id.btn_rd_deg);
            //------------------------------------

            ecran_main = findViewById(R.id.ecran_main); ecran_main.setInputType(InputType.TYPE_CLASS_NUMBER);
            ecran_2 = findViewById(R.id.ecran_2);


            ecran_main.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Ne rien faire avant le changement de texte
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Mettez à jour la position du curseur lors du changement de texte
                    cursorPosition = i + i2;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Obtenez la longueur totale du texte après la modification
                    int newCursorPosition = editable.length();
                    // Déplacez le curseur à la droite après le changement de texte
                    ecran_main.setSelection(newCursorPosition);
                }
            });
            //======== Onclick Listeners ==============

            btn_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setTextColor(getResources().getColor(android.R.color.black));
                    ecran_main.setText("");
                    ecran_2.setText("0");
                }
            });

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Récupérer la position du curseur
                    int cursorPosition = ecran_main.getSelectionStart();

                    // Récupérer le texte actuel de l'EditText
                    String valeur = ecran_main.getText().toString();

                    // Vérifier si la position du curseur n'est pas à la première position
                    if (cursorPosition > 0) {
                        // Créer une StringBuilder avec la valeur actuelle
                        StringBuilder newValue = new StringBuilder(valeur);

                        // Supprimer le caractère à la position du curseur moins un
                        newValue.deleteCharAt(cursorPosition - 1);

                        // Mettre à jour le texte de l'EditText avec la nouvelle valeur
                        ecran_main.setText(newValue.toString());

                        // Déplacer le curseur après la suppression
                        ecran_main.setSelection(cursorPosition - 1);
                    } else {
                        // Si la position du curseur est à la première position, ne rien faire ou effacer tout le texte selon vos besoins
                    }
                }
            });


            btn_p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buton_text = onButtonClick(view);
                }
            });
            btn_p2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buton_text = onButtonClick(view);
                }
            });
            btn_sin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("sin(");
                }
            });
            btn_cos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("cos(");
                }
            });
            btn_tan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("tan(");
                }
            });
            btn_ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("ln(");
                }
            });
            btn_log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("log(");
                }
            });

            btn_racine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String val = ecran_main.getText().toString();
                        double rac = Math.sqrt(Double.parseDouble(val));
                        ecran_main.setText(String.valueOf(decimalFormat.format(rac)));
                        ecran_2.setText(btn_racine.getText() + "" + val);
                    }catch (Exception e){}
                }
            });

            btn_inv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ecran_main.setText(ecran_main.getText() + "^" + "(-1)");
                    }catch (Exception e){}
                }
            });

            btn_fact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        double val = Double.parseDouble(ecran_main.getText().toString());
                        facto = factoriel((int) val);
                        String r = String.valueOf(facto);
                        if (val < 0) {
                            ecran_2.setText("0");
                            ecran_main.setText("Erreurdomaine");
                            ecran_main.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        } else if (r.length() <= 11) {
                            ecran_main.setText(String.valueOf(facto));
                            ecran_2.setText(val + "! ");
                        } else ecran_main.setText(val + "! Trop long");
                    } catch (Exception e) {
                        ecran_2.setText("0");
                    }
                }
            });
            btn_pow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        double d = Double.parseDouble(ecran_main.getText().toString());
                        double square = d * d;
                        ecran_main.setText(String.valueOf(square));
                        ecran_2.setText(d + "²");
                    } catch (Exception ex) {  }
                }
            });
            btn_pow_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String baseText = ecran_main.getText().toString();

                        if (!baseText.isEmpty()) {
                            baseForPowX = Double.parseDouble(baseText);
                            ecran_2.setText(baseForPowX + "^"); // Affiche la base suivie du signe d'exposant
                            ecran_main.setText(""); // Efface l'affichage pour l'entrée de l'exposant
                            waitingForExponent = true; // Indique que l'application attend l'entrée de l'exposant
                        } else {
                            // Gérez le cas où la base est vide
                            showToast("Veuillez entrer une base avant d'utiliser la fonction de puissance.");
                        }
                    } catch (Exception e) { }

                }
            });
            btn_rd_deg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast(btn_rd_deg.getText().toString());
                    // Basculez entre radians et degrés
                    isRadiansMode = !isRadiansMode;
                    // Mettez à jour le texte du bouton en fonction du mode actuel
                    btn_rd_deg.setText(isRadiansMode ? "Rad" : "Deg");
                }
            });

            btn_egale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (waitingForExponent) {
                            String exponentText = ecran_main.getText().toString();

                            if (!exponentText.isEmpty()) {
                                double exponent = Double.parseDouble(exponentText);

                                double result = Math.pow(baseForPowX, exponent);

                                ecran_main.setText(String.valueOf(result));
                                ecran_2.setText(baseForPowX + "^" + exponent); // Efface l'affichage de l'opération de puissance
                                waitingForExponent = false; // Réinitialise le flag après le calcul
                            } else {
                                showToast("Veuillez entrer un exposant.");
                            }
                        }
                        String value = ecran_main.getText().toString();
                        double resultat = egale(value);
                        ecran_main.setText(String.valueOf(resultat));
                        ecran_2.setText(value);
                    } catch (
                            Exception e) {
                        ecran_2.setText("0");
                        ecran_main.setText("Erreur");
                        ecran_main.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        showToast("Erreur de Calcul");
                    }
                }
            });
        } catch (
                Exception ex) {
            ecran_main.setText("");
        }

    }

    //function facto
    public static BigInteger factoriel(int n) {
        BigInteger result = BigInteger.ONE;
        String r = String.valueOf(result);
        if (r.length() <= 11) {
            for (int i = 1; i <= n; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
        }
        return result;
    }

    //Fonction Egale
    public static double egale(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                try {
                    ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                } catch (Exception e) {
                    Toast.makeText(ecran_main.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            boolean eat(int charToEat) {
                try {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                } catch (Exception e) {
                    Toast.makeText(ecran_main.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                try {
                    if (pos < str.length()) {
                        throw new RuntimeException();
                    }
                } catch (Exception e) {
                    ecran_main.setText("");
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) {
                        if (isOperatorNext()) {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }
                        x += parseTerm(); // addition
                    } else if (eat('-')) {
                        if (isOperatorNext()) {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }
                        x -= parseTerm(); // subtraction
                    } else {
                        return x;
                    }
                }
            }

            boolean isOperatorNext() {
                int nextPos = pos;
                while (nextPos < str.length() && str.charAt(nextPos) == ' ') {
                    nextPos++;
                }
                return (nextPos < str.length()) &&
                        (str.charAt(nextPos) == '+' || str.charAt(nextPos) == '-'
                                || str.charAt(nextPos) == '*' || str.charAt(nextPos) == '/');
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) {
                        x *= parseFactor(); // multiplication
                    } else if (eat('/')) {
                        x /= parseFactor(); // division
                    } else if (ch == '(') {
                        // Ajout de la multiplication implicite avec les parenthèses
                        x *= parseFactor();
                    } else if (ch >= 'a' && ch <= 'z') {
                        x *= parseFactor(); // Prendre en compte le facteur actuel
                        x = parseFactor(); // Parsez la fonction trigonométrique
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) {
                        x = isRadiansMode ? Math.sin(Math.toRadians(x)) : Math.sin(x);
                    } else if (func.equals("cos")) {
                        x = isRadiansMode ? Math.cos(Math.toRadians(x)) : Math.cos(x);
                    } else if (func.equals("tan")) {
                        x = isRadiansMode ? Math.tan(Math.toRadians(x)) : Math.tan(x);
                    } else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else {
                        throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                // Nouvelle vérification pour les constantes multiplicatives
                while (Character.isDigit(ch)) {
                    // Consommer les chiffres pour former une constante
                    nextChar();
                }

                if (eat('*')) {
                    // S'il y a un '*' après la constante, multipliez par le résultat de parseFactor()
                    x *= parseFactor();
                } else if (eat('^')) {
                    // Exponentiation après la constante
                    x = Math.pow(x, parseFactor());
                }
                return x;
            }
        }.parse();
    }

    public String onButtonClick(View view) {
        Button button = (Button) view;
        String currentText = ecran_main.getText().toString();
        int cursorPosition = ecran_main.getSelectionStart();

        // Obtenez la partie du texte avant et après la position du curseur
        String textBeforeCursor = currentText.substring(0, cursorPosition);
        String textAfterCursor = currentText.substring(cursorPosition);

        ecran_main.setTextColor(getResources().getColor(android.R.color.black));

        if (ecran_main.getText().equals("Erreur")) {
            ecran_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ecran_main.setText("0");
                }
            });
        } else {
            if (currentText.startsWith("0") && (currentText.length() == 1 || isOperator(currentText.charAt(1)))) {
                ecran_main.setText(String.format("%s%s%s", textBeforeCursor, button.getText(), textAfterCursor));
            } else {
                ecran_main.setText(String.format("%s%s%s", textBeforeCursor, button.getText(), textAfterCursor));
            }
        }
        // Déplacez le curseur à la fin du texte après l'insertion du chiffre
        ecran_main.setSelection(ecran_main.getText().length());
        ecran_2.setText(calculateResult());

        return ecran_main.getText().toString();
    }

    private boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/';
    }

    private String calculateResult() {
        try {
            double result = egale(ecran_main.getText().toString());
            return String.valueOf(result);
        } catch (Exception e) {
            return "";
        }
    }
    //afficher Toast
    private void showToast(String message) {
        SpannableString spannableString = new SpannableString(message);
        //spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#57CDCA"), 0, message.length(), 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), 0);

        // Créez le Toast personnalisé
        Toast toast = Toast.makeText(this, spannableString, Toast.LENGTH_SHORT);
        toast.show();
    }
    //Cycle de vie
    @Override
    protected void onStop() {
        super.onStop();
        showToast("Calculator on Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("Calculator on Resume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        showToast("Calculator on Pause");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("Calculatrice on Destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("Calculatrice on Start");
    }
}