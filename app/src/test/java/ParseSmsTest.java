import com.marcosdiez.extratocartao.glue.SmsParser;
import com.marcosdiez.extratocartao.sms.BankSms;
import com.marcosdiez.extratocartao.sms.SMSData;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParseSmsTest {

    @Test
    public void testAlwaysPass() throws Exception {
        assertEquals(10, 10);
    }

    SMSData mockSms(String message) throws java.text.ParseException {
        SMSData output = new SMSData();

        output.setId(42);
        output.setNumber("+1234");
        output.setBody(message);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = df.parse("15/03/2015 14:28");
        output.setDate(date);

        return output;
    }

    @Test
    public void testBradesco() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "30/07/2015 21:02");
        assertEquals(parsedSms.amount, "41,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "PANINI PIZZA.");
    }

    @Test
    public void testBradescoParcelado() throws Exception {
        String msg = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 05/08/2015 06:46 NO VALOR DE $ 427,00 EM 10 X NO(A) LAVOISIER ANGELICA  SAO PAULO.";
        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BRADESCO");
        assertEquals(parsedSms.nomeCartao, "5761");
        assertEquals(parsedSms.timestamp, "05/08/2015 06:46");
        assertEquals(parsedSms.amount, "427,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "LAVOISIER ANGELICA");
    }

    @Test
    public void testItau() throws Exception {
        String msg = "Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.";
        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

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

        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "ITAU");
        assertEquals(parsedSms.nomeCartao, "1383");
        assertEquals(parsedSms.timestamp, "19/08/2015 08:31");
        assertEquals(parsedSms.amount, "3030,87");
        assertEquals(parsedSms.estabelecimentoAndCidade, "DROGA RAIA F45");


    }

    @Test
    public void testBancoDoBrasil() throws Exception {
        String msg = "BB informa: compra no(a) LOJA DO CENTRO cartao de credito final 1234, valor RS 56,78, em 20/10/14, as 12:33.";

        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "BANCO DO BRASIL");
        assertEquals(parsedSms.nomeCartao, "1234");
        assertEquals(parsedSms.timestamp, "20/10/2014 12:33");
        assertEquals(parsedSms.amount, "56,78");
        assertEquals(parsedSms.estabelecimentoAndCidade, "LOJA DO CENTRO");
    }

    @Test
    public void testSantanderA() throws Exception {
        String msg = "Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 16,90 aprovada em 05/08/15 as 16:13 SMARTCOOKING COM DE";

        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "3031");
        assertEquals(parsedSms.timestamp, "05/08/2015 16:13");
        assertEquals(parsedSms.amount, "16,90");
        assertEquals(parsedSms.estabelecimentoAndCidade, "SMARTCOOKING COM DE");

    }

    @Test
    public void testSantanderB() throws Exception {
        String msg = "Santander Informa: Transacao Cartao VISA final 8304 de R$ 24,59 aprovada em 01/08/15 as 22:36 APL* ITUNES.COM/BILL";

        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "8304");
        assertEquals(parsedSms.timestamp, "01/08/2015 22:36");
        assertEquals(parsedSms.amount, "24,59");
        assertEquals(parsedSms.estabelecimentoAndCidade, "APL* ITUNES.COM/BILL");
    }


    @Test
    public void testSantanderC() throws Exception {
        String msg = "Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 33,00 aprovada em 03/08/15 as 17:42 AUTO POSTO NOVA V";

        BankSms parsedSms = SmsParser.parseSms(mockSms(msg));

        assertEquals(parsedSms.nomeBanco, "SANTANDER");
        assertEquals(parsedSms.nomeCartao, "0211");
        assertEquals(parsedSms.timestamp, "03/08/2015 17:42");
        assertEquals(parsedSms.amount, "33,00");
        assertEquals(parsedSms.estabelecimentoAndCidade, "AUTO POSTO NOVA V");
    }

}
