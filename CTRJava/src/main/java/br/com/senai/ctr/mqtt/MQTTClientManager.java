package br.com.senai.ctr.mqtt;

import br.com.senai.ctr.App;
import br.com.senai.ctr.CTRLogs;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTClientManager {

    private MqttClient cliente;

    public void conectar(String url_mqtt_server, String id_ctr_broker, MQTTClientCallback callback) {
        if (cliente == null) {
            try {
                cliente = new MqttClient(url_mqtt_server, id_ctr_broker);
                cliente.setCallback(callback);
                cliente.connect();
                CTRLogs.log("Conectado a " + url_mqtt_server + " como " + id_ctr_broker);
                cliente.subscribe(id_ctr_broker);
                CTRLogs.log("Escutando pedidos em " + id_ctr_broker);
            } catch (MqttException e) {
                e.printStackTrace();
                cliente = null;
            }
        }
    }

    public void publicar(String topico, byte[] payload) {
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(payload);
            cliente.publish(topico, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
