package fr.istic.vv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class TLSSocketFactoryTestMocks {

    @Mock
    private SSLSocket socket;

    @Test
    public void preparedSocket_NullProtocols()  {
        TLSSocketFactory f = new TLSSocketFactory();

        when(socket.getSupportedProtocols()).thenReturn(null);
        when(socket.getEnabledProtocols()).thenReturn(null);
        doAnswer(fail()).when(socket).setEnabledProtocols(any(String[].class));

        f.prepareSocket(socket);

        verify(socket, times(1)).getSupportedProtocols();
        verify(socket, times(1)).getEnabledProtocols();
        verify(socket, times(1)).setEnabledProtocols(any(String[].class));

    }

    @Test
    public void typical()  {
        TLSSocketFactory f = new TLSSocketFactory();

        when(socket.getSupportedProtocols()).thenReturn(shuffle(new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}));
        when(socket.getEnabledProtocols()).thenReturn(shuffle(new String[]{"SSLv3", "TLSv1"}));

        doAnswer(invocation -> {
            String[] protocols = invocation.getArgument(0);
            assertArrayEquals(protocols, new String[] {"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" });
            return null;
        }).when(socket).setEnabledProtocols(any(String[].class));

        f.prepareSocket(socket);

        verify(socket, times(1)).getSupportedProtocols();
        verify(socket, times(1)).getEnabledProtocols();
        verify(socket, times(1)).setEnabledProtocols(any(String[].class));
    }


    private String[] shuffle(String[] in) {
        List<String> list = new ArrayList<String>(Arrays.asList(in));
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }

}