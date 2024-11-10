package cn.gzy.exception;

public class TokenErrorException extends Exception{
    public TokenErrorException(){
        super("token is not right。");
    }

    public TokenErrorException(String msg){
        super(msg);
    }
}
