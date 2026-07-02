package entities;

import java.util.Random;

public final class Pagamento {
    public boolean verificarCartao(String numeroCartao) {
        if (numeroCartao.length() == 16) {
            return true;
        }
        else
            return false;
    }

    public int gerarNumeroPedido(){
        Random rand = new Random();
        return rand.nextInt(1000000);
    }
}