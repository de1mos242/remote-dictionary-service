package net.de1mos.remotedictionaryservice.cliclient;

import org.junit.Test;

import static org.junit.Assert.*;

public class CliAddressParserTest {

    @Test
    public void testValidAddressDNSWithoutPort() {
        final CliAddressParser parser = new CliAddressParser("de1mos.net");
        assertTrue(parser.isValid());
        assertEquals("de1mos.net", parser.getAddressPart());
        assertEquals(80, parser.getPort());
    }

    @Test
    public void testValidAddressDNSWithPort() {
        final CliAddressParser parser = new CliAddressParser("de1mos.net:8080");
        assertTrue(parser.isValid());
        assertEquals("de1mos.net", parser.getAddressPart());
        assertEquals(8080, parser.getPort());
    }

    @Test
    public void testValidAddressIPWithoutPort() {
        final CliAddressParser parser = new CliAddressParser("192.168.25.1");
        assertTrue(parser.isValid());
        assertEquals("192.168.25.1", parser.getAddressPart());
        assertEquals(80, parser.getPort());
    }

    @Test
    public void testValidAddressIPWithPort() {
        final CliAddressParser parser = new CliAddressParser("192.168.25.1:8080");
        assertTrue(parser.isValid());
        assertEquals("192.168.25.1", parser.getAddressPart());
        assertEquals(8080, parser.getPort());
    }

    @Test
    public void testInvalidAddresses() {
        assertFalse(new CliAddressParser("127.10.0.1:f").isValid());

        assertFalse(new CliAddressParser("http://127.10.0.1:8080").isValid());
        assertFalse(new CliAddressParser("127.10.0.1:999/path").isValid());
        assertFalse(new CliAddressParser("127.10.0.1:999/").isValid());
    }

}