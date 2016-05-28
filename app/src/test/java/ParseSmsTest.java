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
    public void testBradescoDoBrasilV2() throws Exception {
        String msg = "BB: compra PAYPAL DO BRASI, cartao final 2567, RS 148,39 - 19/01 - 19:46.";

        BankSms parsedSms = SmsParser.parseSms(Util.mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "2567");
        assertEquals(parsedSms.timestamp, "19/01/2015 19:46");
        assertEquals(parsedSms.amount, "148,39");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PAYPAL DO BRASI");
    }

    @Test
    public void testBradescoDoBrasilV3() throws Exception {
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
