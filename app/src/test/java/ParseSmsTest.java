import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.glue.SmsParser;
import com.marcosdiez.extratocartao.sms.BankSms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParseSmsTest {

    @Test
    public void testAlwaysPass() throws Exception {
        assertEquals(10, 10);
    }


    @Test
    public void testBradesco() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "30/07/2015 21:02");
        assertEquals(parsedSms.amount, "41,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PANINI PIZZA.");
    }

    @Test
    public void testBradesco20180402() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 2561 EM 02/04/2018 18:51. VALOR DE R$ 31,12 NO(A) DIA 9888                 SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "2561");
        assertEquals(parsedSms.timestamp, "02/04/2018 18:51");
        assertEquals(parsedSms.amount, "31,12");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DIA 9888");
    }

    @Test
    public void testBradescoMilReailsA() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 1,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "1,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }

    @Test
    public void testBradescoMilReailsB() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 13,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "13,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }

    @Test
    public void testBradescoMilReailsC() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 130,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "130,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }


    @Test
    public void testBradescoZZ() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 2561 EM 30/05/2018 11:15. VALOR DE R$ 25,30 ALQUIMIA COMER LTDA      SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "2561");
        assertEquals(parsedSms.timestamp, "30/05/2018 11:15");
        assertEquals(parsedSms.amount, "25,30");
        assertEquals(parsedSms.estabelecimentoAndCidade, "ALQUIMIA COMER LTDA");
    }

    @Test
    public void testBradescoZZ2() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 2561 EM 23/05/2018 14:54 NO VALOR DE R$ 169,14 EM 2 X SONDA AGUA BRANCA        SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "2561");
        assertEquals(parsedSms.timestamp, "23/05/2018 14:54");
        assertEquals(parsedSms.amount, "169,14");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SONDA AGUA BRANCA");
    }


    @Test
    public void testBradescoMilReailsD() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 1.300,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "1.300,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }

    @Test
    public void testBradescoMilReails() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 10.300,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "10.300,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }

    @Test
    public void testBradescoMilReailsE() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/08/2015 17:02. VALOR DE $ 100.300,00 NO(A) SPECIAL CAR   SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/08/2015 17:02");
        assertEquals(parsedSms.amount, "100.300,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SPECIAL CAR");
    }


    @Test
    public void testBradescoParcelado() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 05/08/2015 06:46 NO VALOR DE $ 427,00 EM 10 X NO(A) LAVOISIER ANGELICA  SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "05/08/2015 06:46");
        assertEquals(parsedSms.amount, "427,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "LAVOISIER ANGELICA");
    }

    @Test
    public void testBradescoParcelado2() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 2561 EM 29/03/2018 07:01 NO VALOR DE R$ 1.069,14 EM 6 X NO(A) DUFRY LOJAS FRANCAS LT   GUARULHOS.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "2561");
        assertEquals(parsedSms.timestamp, "29/03/2018 07:01");
        assertEquals(parsedSms.amount, "1.069,14");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DUFRY LOJAS FRANCAS LT");
    }


    @Test
    public void testBradescoDoisReais() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 28/04/2016 18:26. VALOR DE $ 2,00 NO(A) SATADIUM CAFE BARUERI.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "28/04/2016 18:26");
        assertEquals(parsedSms.amount, "2,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SATADIUM CAFE BARUERI");
    }

    @Test
    public void testBradescoX() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 4642 EM 30/11/2016 18:28. VALOR DE $ 18,36 NO(A) DROGA RAIA F46           SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "4642");
        assertEquals(parsedSms.timestamp, "30/11/2016 18:28");
        assertEquals(parsedSms.amount, "18,36");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DROGA RAIA F46");
    }

    @Test
    public void testBradescoY() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 4642 EM 01/12/2016 19:49. VALOR DE $ 11,00 NO(A) SUBWAY CARDOSO           SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "4642");
        assertEquals(parsedSms.timestamp, "01/12/2016 19:49");
        assertEquals(parsedSms.amount, "11,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SUBWAY CARDOSO");
    }



    @Test
    public void testItau() throws Exception {
        String msg = "Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "1976");
        assertEquals(parsedSms.timestamp, "01/08/2015 13:59");
        assertEquals(parsedSms.amount, "9,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PALETERIA CAMPO BELO");
        // itau
        // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.

    }

    @Test
    public void testItauB() throws Exception {
        String msg = "compra aprovada no seu PERSON MULT MC PLAT final 1383 - DROGA RAIA F45 valor RS 3030,87 em 19/08, as 08h31.";
                   // Compra aprovada no seu ITAUCARD 2.0 VS NAC final 1158 - AUTO POSTO BEIRA BAIXA valor RS   50,00 em 03/10, as 05h38.

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "1383");
        assertEquals(parsedSms.timestamp, "19/08/2015 08:31");
        assertEquals(parsedSms.amount, "3030,87");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DROGA RAIA F45");
    }


    @Test
    public void testItauPersonanaliteA() throws Exception {
        String msg = "ITAU PERSONNALITE: Cartao final 4965 COMPRA APROVADA 18/09 22:50:19 R$ 86,00 Local: BETO COM.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4965");
        assertEquals(parsedSms.timestamp, "18/09/2015 22:50:19");
        assertEquals(parsedSms.amount, "86,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "BETO COM");
    }

    @Test
    public void testItauPersonanaliteB() throws Exception {
        String msg = "ITAU PERSONNALITE: Cartao final 4965 COMPRA APROVADA 18/09 07:52:59 R$ 149,08 Local: AUTO POST.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4965");
        assertEquals(parsedSms.timestamp, "18/09/2015 07:52:59");
        assertEquals(parsedSms.amount, "149,08");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO POST");
    }


    @Test
    public void testItauPersonanaliteC() throws Exception {
        String msg = "ITAU PERSONNALITE: Cartao final 4695 COMPRA APROVADA 25/07 13:23:58 R$ 1.000,00 Local: PINGUIMGA.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4695");
        assertEquals(parsedSms.timestamp, "25/07/2015 13:23:58");
        assertEquals(parsedSms.amount, "1.000,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PINGUIMGA");
    }


    @Test
    public void testItauPersonanaliteSaque() throws Exception {
        String msg = "ITAU PERSONNALITE: Cartao final 4695 SAQUE APROVADO 26/07 15:15:37 R$ 400,00 Local: CX ITAU AV NACOES UN.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4695");
        assertEquals(parsedSms.timestamp, "26/07/2015 15:15:37");
        assertEquals(parsedSms.amount, "400,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "CX ITAU AV NACOES UN");
    }

    @Test
    public void testItauPersonnaliteSaqueSemCartao() throws Exception {
        String msg = "ITAU PERSONNALITE: SAQUE APROVADO 29/09 21:08:22 R$ 150,00 Local: CX ITAU AV NACOES UN.";
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "0000"); // o certo seria DIGITAL, mas iria acabar com o layout
        assertEquals(parsedSms.timestamp, "29/09/2015 21:08:22");
        assertEquals(parsedSms.amount, "150,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "CX ITAU AV NACOES UN");
    }


    @Test
    public void testBancoDoBrasil() throws Exception {
        // String msg = "BB informa: compra no(a) LOJA DO CENTRO cartao de credito final 1234, valor RS 56,78, em 20/10/14, as 12:33.";
        String msg = "BB avisa: compra PANIFICACAO URC, cartao final 5597, RS 39,94 - 11/10-16:18. Responda BL5597 se quiser bloquear cartao. Lim disp RS 9.534";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "5597");
        assertEquals(parsedSms.timestamp, "11/10/2015 16:18");
        assertEquals(parsedSms.amount, "39,94");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PANIFICACAO URC");
    }

    @Test
    public void testBancoDoBrasilDebito2() throws Exception {
        String msg = "BB: compra em POSTO MAXIMO IPIRANG, cartao de debito final 5137, RS 251,91, em 02/03/18. Saldo c/c: RS  2.939,91D.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL DEBITO");
        assertEquals(parsedSms.nomeCartao, "5137");
        assertEquals(parsedSms.timestamp, "02/03/2018 02:28"); // a hora vem do SMS, no caso mockSms
        assertEquals(parsedSms.amount, "251,91");
        assertEquals(parsedSms.estabelecimentoAndCidade, "POSTO MAXIMO IPIRANG");
    }

    @Test
    public void testSantanderA() throws Exception {
        String msg = "Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 16,90 aprovada em 05/08/15 as 16:13 SMARTCOOKING COM DE";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "3031");
        assertEquals(parsedSms.timestamp, "05/08/2015 16:13");
        assertEquals(parsedSms.amount, "16,90");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SMARTCOOKING COM DE");

    }

    @Test
    public void testSantanderB() throws Exception {
        String msg = "Santander Informa: Transacao Cartao VISA final 8304 de R$ 24,59 aprovada em 01/08/15 as 22:36 APL* ITUNES.COM/BILL";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "8304");
        assertEquals(parsedSms.timestamp, "01/08/2015 22:36");
        assertEquals(parsedSms.amount, "24,59");
        assertEquals(parsedSms.estabelecimentoAndCidade, "APL* ITUNES.COM/BILL");
    }


    @Test
    public void testSantanderC() throws Exception {
        String msg = "Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 33,00 aprovada em 03/08/15 as 17:42 AUTO POSTO NOVA V";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "0211");
        assertEquals(parsedSms.timestamp, "03/08/2015 17:42");
        assertEquals(parsedSms.amount, "33,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO POSTO NOVA V");
    }


    @Test
    public void testBradescoDebitoA() throws Exception {
        String msg = "25/09/15 11:53 BRADESCO Maikon: Compra cartao deb. final 8108 de 1,50 realizada no estab. FAMILIA PEPERON.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "8108");
        assertEquals(parsedSms.timestamp, "25/09/15 11:53");
        assertEquals(parsedSms.amount, "1,50");
        assertEquals(parsedSms.estabelecimentoAndCidade, "FAMILIA PEPERON");
    }


    @Test
    public void testBradescoDebitoB() throws Exception {
        String msg = "24/09/15 21:02 BRADESCO Maikon: Compra cartao deb. final 8108 de 69,00 realizada no estab. REPLAY LANCH E.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "8108");
        assertEquals(parsedSms.timestamp, "24/09/15 21:02");
        assertEquals(parsedSms.amount, "69,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "REPLAY LANCH E");
    }

    @Test
    public void testBradescoDebitoC() throws Exception {
        String msg = "25/09/15 09:39 BRADESCO Maikon: Compra cartao deb. final 8108 de 4,30 realizada no estab. FAMILIA PEPERON.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "8108");
        assertEquals(parsedSms.timestamp, "25/09/15 09:39");
        assertEquals(parsedSms.amount, "4,30");
        assertEquals(parsedSms.estabelecimentoAndCidade, "FAMILIA PEPERON");
    }

    @Test
    public void testBancoDoBrasilV2() throws Exception {
        String msg = "BB: compra PAYPAL DO BRASI, cartao final 2567, RS 148,39 - 19/01 - 19:46.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "2567");
        assertEquals(parsedSms.timestamp, "19/01/2015 19:46");
        assertEquals(parsedSms.amount, "148,39");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAYPAL DO BRASI");
    }

    @Test
    public void testBancoDoBrasilDebito() throws Exception {
        String msg = "BB: compra RS  37,50 LANCH0S BURDOG cartao final 4689 em 06/06/16";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL DEBITO");
        assertEquals(parsedSms.nomeCartao, "4689");
        assertEquals(parsedSms.timestamp, "06/06/2016 02:28"); // a hora vem do SMS, no caso mockSms
        assertEquals(parsedSms.amount, "37,50");
        assertEquals(parsedSms.estabelecimentoAndCidade, "LANCH0S BURDOG");
    }


    @Test
    public void testBancoDoBrasilContaCorrente() throws Exception {
        String msg = "BB: debito referente transferencia em 07/03/18, as 13:02, RS  265,00, canal Internet. Responda NR0292 para bloquear suas senhas.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "Conta Corrente");
        assertEquals(parsedSms.timestamp, "07/03/2018 13:02"); // a hora vem do SMS, no caso mockSms
        assertEquals(parsedSms.amount, "265,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "Internet");
    }



    @Test
    public void testBancoDoBrasilV3() throws Exception {
        String msg = "BB: compra DROGA FUJI, cartao final 2567, RS 35,45 - 19/01 - 18:15.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "2567");
        assertEquals(parsedSms.timestamp, "19/01/2015 18:15");
        assertEquals(parsedSms.amount, "35,45");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DROGA FUJI");
    }


    @Test
    public void testSicredi() throws Exception {
        String msg = "TRANSACAO APROVADA CARTAO FINAL 0110, PARC=102MERCADOPAGO*MLIVR, 199,00, 28/01/2016 AS 13:58:16.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SICREDI");
        assertEquals(parsedSms.nomeCartao, "0110");
        assertEquals(parsedSms.timestamp, "28/01/2016 13:58");
        assertEquals(parsedSms.amount, "199,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PARC=102MERCADOPAGO*MLIVR");
    }

    @Test
    public void testSicredi2() throws Exception {
        String msg = "SEGURANCA: TRANSACAO APROVADA CARTAO FINAL 0110, PARC=102PagSegro Propag, 362,34, 07/02/2016 AS 18:13:21.DUVIDAS CONTATAR A CENTRAL DE ATENDIMENTO";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SICREDI");
        assertEquals(parsedSms.nomeCartao, "0110");
        assertEquals(parsedSms.timestamp, "07/02/2016 18:13");
        assertEquals(parsedSms.amount, "362,34");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PARC=102PagSegro Propag");
    }

    // SEGURANCA: TRANSACAO APROVADA CARTAO FINAL 3113, REST HIKARI, 14,76, 24/01/2017 AS 12:43:14 SICREDI 3003-4770/0800-724-4770

    @Test
    public void testSicredi3() throws Exception {
        String msg = "SEGURANCA: TRANSACAO APROVADA CARTAO FINAL 3113, REST HIKARI, 14,76, 24/01/2017 AS 12:43:14 SICREDI 3003-4770/0800-724-4770";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SICREDI");
        assertEquals(parsedSms.nomeCartao, "3113");
        assertEquals(parsedSms.timestamp, "24/01/2017 12:43");
        assertEquals(parsedSms.amount, "14,76");
        assertEquals(parsedSms.estabelecimentoAndCidade, "REST HIKARI");
    }


    @Test
    public void testSicoob() throws Exception {
        String msg = "SICOOB informa, compra Credito aprovada com seu cartao MasterCard - DAFITI, em 10/02 as 07:10h, valor R$107,99";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SICOOB");
        assertEquals(parsedSms.nomeCartao, "MasterCard");
        assertEquals(parsedSms.timestamp, "10/02/2015 07:10");
        assertEquals(parsedSms.amount, "107,99");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DAFITI");

    }

    @Test
    public void testSicoob2() throws Exception {
        String msg = "SICOOB informa, compra Credito aprovada com seu cartao Mastercard - PAGSEGUROContract, em 06/05 as 22:19h, valor R$1.100,70";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SICOOB");
        assertEquals(parsedSms.nomeCartao, "Mastercard");
        assertEquals(parsedSms.timestamp, "06/05/2015 22:19");
        assertEquals(parsedSms.amount, "1.100,70");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAGSEGUROContract");

    }

    @Test
    public void testCredicard1() throws Exception {
        String msg = "Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - NET PARK valor RS 25,00 em 02/02, as 14h38.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "02/02/2015 14:38");
        assertEquals(parsedSms.amount, "25,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "NET PARK");

    }

    @Test
    public void testCredicard2() throws Exception {
        String msg = "Compra aprovada no CREDICARD EXCLUS MC p/ CAROLINE KISS - SALAO DA PATRICIA valor RS 15,00 em 02/02/2016 as 15h27.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "CAROLINE KISS");
        assertEquals(parsedSms.timestamp, "02/02/2016 15:27");
        assertEquals(parsedSms.amount, "15,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SALAO DA PATRICIA");

    }

    @Test
    public void testCredicard3() throws Exception {
        String msg = "Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - MC DONALDS IPI valor RS 21,00 em 03/02, as 18h34.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "03/02/2015 18:34");
        assertEquals(parsedSms.amount, "21,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "MC DONALDS IPI");

    }

    @Test
    public void testCredicard4() throws Exception {
        String msg = "Compra aprovada no CREDICARD EXCLUS MC p/ VALDEMIRA N QUEIROZ - CIRURGICA SINETE valor RS 213,34 em 20/01/2016 as 09h33.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "VALDEMIRA N QUEIROZ");
        assertEquals(parsedSms.timestamp, "20/01/2016 09:33");
        assertEquals(parsedSms.amount, "213,34");
        assertEquals(parsedSms.estabelecimentoAndCidade, "CIRURGICA SINETE");

    }



    @Test
    public void testCredicard5() throws Exception {
        String msg = "Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - ESTACAO DE SERVICOS AU valor RS 87,56 em 21/01, as 10h03.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "21/01/2015 10:03");
        assertEquals(parsedSms.amount, "87,56");
        assertEquals(parsedSms.estabelecimentoAndCidade, "ESTACAO DE SERVICOS AU");
    }


    @Test
    public void testCredicard6() throws Exception {
        String msg = "Compra aprovada no CREDICARD EXCLUS MC p/ CAROLINE KISS - SALAO DA PATRICIA valor RS 200,00 em 21/01/2016 as 15h57.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "CAROLINE KISS");
        assertEquals(parsedSms.timestamp, "21/01/2016 15:57");
        assertEquals(parsedSms.amount, "200,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SALAO DA PATRICIA");
    }

    @Test
    public void testCredicard7() throws Exception {
        String msg = "Compra aprovada no seu CREDICARD EXLCUS MC final 4897 - AUTO VIACAO BRAGANCA valor RS 25,70 em 06/02, as 10h03.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "06/02/2015 10:03");
        assertEquals(parsedSms.amount, "25,70");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO VIACAO BRAGANCA");
    }


    @Test
    public void testCredicard8() throws Exception {
        String msg = "Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - INVENTURE RESTARANTES valor RS 30,50 em 13/08, as 15h28.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "13/08/2015 15:28");
        assertEquals(parsedSms.amount, "30,50");
        assertEquals(parsedSms.estabelecimentoAndCidade, "INVENTURE RESTARANTES");
    }

    @Test
    public void testCredicard9() throws Exception {
        String msg = "Compra aprovada no CREDICARD EXCLUS MC final 4897 - MOVIDA RENT A CAR - RS 12.000,00 em 13/08/2016 as 17h20. Utilizando 80% do limite.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4897");
        assertEquals(parsedSms.timestamp, "13/08/2016 17:20");
        assertEquals(parsedSms.amount, "12.000,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "MOVIDA RENT A CAR");
    }

//    Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - INVENTURE RESTARANTES valor RS 30,50 em 13/08, as 15h28.
//    Compra aprovada no CREDICARD EXCLUS MC final 4897 - MOVIDA RENT A CAR - RS 12.000,00 em 13/08/2016 as 17h20. Utilizando 80% do limit.


    @Test
    public void testItaucard2() throws Exception {
        String msg = "Compra aprovada no seu ITAUCARD 2.0 VS NAC final 1158 - AUTO POSTO BEIRA BAIXA valor RS 50,00 em 03/10, as 05h38.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "1158");
        assertEquals(parsedSms.timestamp, "03/10/2015 05:38");
        assertEquals(parsedSms.amount, "50,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO POSTO BEIRA BAIXA");
    }

//    Compra aprovada no seu CREDICARD EXCLUS MC final 4897 - INVENTURE RESTARANTES valor RS 30,50 em 13/08, as 15h28.
//    Compra aprovada no CREDICARD EXCLUS MC final 4897 - MOVIDA RENT A CAR - RS 12.000,00 em 13/08/2016 as 17h20. Utilizando 80% do limit.




    @Test
    public void testItauDebito() throws Exception {
        String msg = "ITAU DEBITO: Cartao final 4607 COMPRA APROVADA 16/02 18:21:18 R$ 8,25 Local: DENTNHO&apos;.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "4607");
        assertEquals(parsedSms.timestamp, "16/02/2015 18:21:18");
        assertEquals(parsedSms.amount, "8,25");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DENTNHO&apos;");
    }

    @Test
    public void testItauDebito3() throws Exception {
        String msg = "ITAU DEBITO: Cartao final 5631 COMPRA APROVADA 05/10 13:36:34 R 88,50 Local: PONTO DA.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "5631");
        assertEquals(parsedSms.timestamp, "05/10/2015 13:36:34");
        assertEquals(parsedSms.amount, "88,50");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PONTO DA");
    }



    @Test
    public void testItauDebitoB() throws Exception {
        String msg = "ITAU DEBITO: Cartao final 2929 COMPRA APROVADA 28/02 21:48:56 R$ 15,00 Local: PAGSEGURO.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "2929");
        assertEquals(parsedSms.timestamp, "28/02/2015 21:48:56");
        assertEquals(parsedSms.amount, "15,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAGSEGURO");
    }

    @Test
    public void testPortoSeguroA() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 1110 no valor de R$ 96,00 em 06/03 as 20h05. E C S H LTDA";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "1110");
        assertEquals(parsedSms.timestamp, "06/03/2015 20:05");
        assertEquals(parsedSms.amount, "96,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "E C S H LTDA");
    }


    @Test
    public void testPortoSeguroB() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 1110 no valor de R$ 89,00 em 08/03 as 15h23. P STATION";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "1110");
        assertEquals(parsedSms.timestamp, "08/03/2015 15:23");
        assertEquals(parsedSms.amount, "89,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "P STATION");
    }

    @Test
    public void testPortoSeguroC() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 4117 no valor de R$ 131,41 em 08/05 as 07h57. PST 387 PCP CARREFOUR";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "4117");
        assertEquals(parsedSms.timestamp, "08/05/2015 07:57");
        assertEquals(parsedSms.amount, "131,41");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PST 387 PCP CARREFOUR");
    }


    @Test
    public void testPortoSeguroD() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 4117 no valor de R$ 161,52 em 20/05 as 20:07 DALEN SUPERMERCADOS.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "4117");
        assertEquals(parsedSms.timestamp, "20/05/2015 20:07");
        assertEquals(parsedSms.amount, "161,52");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DALEN SUPERMERCADOS");
    }


    @Test
    public void testPortoSeguroE() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 4216 no valor de R$ 109,99 em 22/05 as 16:24 PBKIDS.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "4216");
        assertEquals(parsedSms.timestamp, "22/05/2015 16:24");
        assertEquals(parsedSms.amount, "109,99");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PBKIDS");
    }


    @Test
    public void testPostorSeguroF() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO. http://porto.vc/cartaoapp baixe";
        // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "9999");
        assertEquals(parsedSms.timestamp, "26/11/2015 13:13");
        assertEquals(parsedSms.amount, "99,90");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PEIXE URBANO");

    }


    @Test
    public void testPortoSeguroG() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao MASTERCARD final 6117 no valor de R$ 78,75 em 03/09 as 18h59. O PIRATA FRUTOS DO MAR";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "6117");
        assertEquals(parsedSms.timestamp, "03/09/2015 18:59");
        assertEquals(parsedSms.amount, "78,75");
        assertEquals(parsedSms.estabelecimentoAndCidade, "O PIRATA FRUTOS DO MAR");
    }


    @Test
    public void testPostorSeguroH() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada VISA final 9999 R$ 99,99 em 29/11 as 13:47 2033DROGASIL. http://porto.vc/cartaoapp baixe o novo app";
        // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "9999");
        assertEquals(parsedSms.timestamp, "29/11/2015 13:47");
        assertEquals(parsedSms.amount, "99,99");
        assertEquals(parsedSms.estabelecimentoAndCidade, "2033DROGASIL");

    }

    @Test
    public void testPostorSeguroI() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada no cartao VISA final 9999 no valor de R¤ 63,45 em 18/10 as 21:48. PAO DE ACUCAR";
        // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "9999");
        assertEquals(parsedSms.timestamp, "18/10/2015 21:48");
        assertEquals(parsedSms.amount, "63,45");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAO DE ACUCAR");

    }

    @Test
    public void testPostorSeguroJ() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada VISA final 9999 R¤ 115,27 em 09/11 as 20:04. AUTO POSTO UNIVERSITAR . Novidade! Baixe o app do cartao: http://porto.vc/appcartao";
        // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "9999");
        assertEquals(parsedSms.timestamp, "09/11/2015 20:04");
        assertEquals(parsedSms.amount, "115,27");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO POSTO UNIVERSITAR");

    }

    @Test
    public void testPostorSeguroK() throws Exception {
        String msg = "Porto Cartoes: Compra aprovada VISA final 9999 R$ 348,75 em 29/11 as 13:47 2033DROGASIL. http://porto.vc/cartaoapp baixe o novo app";
        // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO
        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Porto Seguro");
        assertEquals(parsedSms.nomeCartao, "9999");
        assertEquals(parsedSms.timestamp, "29/11/2015 13:47");
        assertEquals(parsedSms.amount, "348,75");
        assertEquals(parsedSms.estabelecimentoAndCidade, "2033DROGASIL");

    }


//
//
//
//
//


    @Test
    public void testCaixa() throws Exception {
        String msg = "CAIXA Informa: Compra aprovada no(a) RAFAELA SUELEN, R$ 25,00, 01/04 as 17:55, cartao MASTERCARD final 1549.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "CAIXA");
        assertEquals(parsedSms.nomeCartao, "1549");
        assertEquals(parsedSms.timestamp, "01/04/2015 17:55");
        assertEquals(parsedSms.amount, "25,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "RAFAELA SUELEN");
    }

    @Test
    public void testItauUniclassA() throws Exception {
        String msg = "ITAU UNICLASS: Cartao final 2681 COMPRA APROVADA 01/04 20:38:42 R$ 50,00 Local: Centro Au. Consulte tambem pelo celular";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "2681");
        assertEquals(parsedSms.timestamp, "01/04/2015 20:38:42");
        assertEquals(parsedSms.amount, "50,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "Centro Au");
    }

    @Test
    public void testItauUniclassB() throws Exception {
        String msg = "ITAU UNICLASS: Cartao final 2681 COMPRA APROVADA 01/04 22:12:47 R$ 31,00 Local: SHOCK POI. Consulta tambem pelo celular";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "2681");
        assertEquals(parsedSms.timestamp, "01/04/2015 22:12:47");
        assertEquals(parsedSms.amount, "31,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SHOCK POI");
    }

    @Test
    public void testItauUniclassC() throws Exception {
        String msg = "ITAU UNICLASS: Cartao final 2681 COMPRA APROVADA 02/04 21:31:51 R$ 29,00 Local: PIZZARIA. Consulte tambm pelo celular";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "2681");
        assertEquals(parsedSms.timestamp, "02/04/2015 21:31:51");
        assertEquals(parsedSms.amount, "29,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PIZZARIA");
    }

    @Test
    public void testUniCred1() throws Exception {
        String msg = "Unicred informa: compra credito aprovada no cartao MasterCard em 02/04, 13:57, de R$94,32. Local: BIG CAMBORIU 200.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Unicred");
        assertEquals(parsedSms.nomeCartao, "MasterCard");
        assertEquals(parsedSms.timestamp, "02/04/2015 13:57");
        assertEquals(parsedSms.amount, "94,32");
        assertEquals(parsedSms.estabelecimentoAndCidade, "BIG CAMBORIU 200");

    }

    @Test
    public void testUniCredB() throws Exception {
        String msg = "Unicred informa: compra credito aprovada no cartao MasterCard em 02/04, 17:51, de R$358,80. Local: PAGSEGURO*PagSegu.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "Unicred");
        assertEquals(parsedSms.nomeCartao, "MasterCard");
        assertEquals(parsedSms.timestamp, "02/04/2015 17:51");
        assertEquals(parsedSms.amount, "358,80");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAGSEGURO*PagSegu");

    }

    @Test
    public void testItauDebito2() throws Exception {
        String msg = "ITAU DEBITO: Cartao final XXXX COMPRA APROVADA 25/04 19:22:08 R$ 142,70 Local: FARMA NIL. Consulte tambem pelo celular www.itau.com.br";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "XXXX");
        assertEquals(parsedSms.timestamp, "25/04/2015 19:22:08");
        assertEquals(parsedSms.amount, "142,70");
        assertEquals(parsedSms.estabelecimentoAndCidade, "FARMA NIL");

    }


    @Test
    public void testBanrisul() throws Exception {
        // Banrisul informa:
        String msg = "Banrisul informa: APROVADA TRANSACAO CARTAO DE CREDITO DE 150,00 AS 13:19:31 DE 05/05/2016 CARTAO FINAL 2123 FARMA LEVES";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANRISUL");
        assertEquals(parsedSms.nomeCartao, "2123");
        assertEquals(parsedSms.timestamp, "05/05/2016 13:19:31");
        assertEquals(parsedSms.amount, "150,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "FARMA LEVES");

    }



}
