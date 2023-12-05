package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import org.springframework.stereotype.Component;

@Component
public class PasswordMethods {

    public PasswordDto convertPasswordDocumentToDto(PasswordDocument passwordDocument){
        return PasswordDto.builder()
                .password(passwordDocument.getPassword())
                .secure(isSecure(passwordDocument.getPassword()) ? "Es segura" : "No es tan segura" )
                .build();
    }

    public String generaPassword (int length){
        String password="";
        for (int i=0;i<length;i++){
            //Generamos un numero aleatorio, segun este elige si a�adir una minuscula, mayuscula o numero
            int eleccion=((int)Math.floor(Math.random()*3+1));

            if (eleccion==1){
                char minusculas=(char)((int)Math.floor(Math.random()*(123-97)+97));
                password+=minusculas;
            }else{
                if(eleccion==2){
                    char mayusculas=(char)((int)Math.floor(Math.random()*(91-65)+65));
                    password+=mayusculas;
                }else{
                    char numeros=(char)((int)Math.floor(Math.random()*(58-48)+48));
                    password+=numeros;
                }
            }
        }
        return password;
    }

    public boolean isSecure(String password){
        int cuentanumeros=0;
        int cuentaminusculas=0;
        int cuentamayusculas=0;
        //Vamos caracter a caracter y comprobamos que tipo de caracter es
        for (int i=0;i<password.length();i++){
            if (password.charAt(i)>=97 && password.charAt(i)<=122){
                cuentaminusculas+=1;
            }else{
                if (password.charAt(i)>=65 && password.charAt(i)<=90){
                    cuentamayusculas+=1;
                }else{
                    cuentanumeros+=1;
                }
            }
        }
        //Si la constraseña tiene mas de 5 numeros, mas de 1 minuscula y mas de 2 mayusculas
        if (cuentanumeros>=5 && cuentaminusculas>=1 && cuentamayusculas>=2){
            return true;
        }else{
            return false;
        }
    }
}
