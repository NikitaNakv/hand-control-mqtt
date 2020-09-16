package emulator

import emulator.models.MqttDataModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Эмулятор бионического протеза руки. Поддерживает протокол протеза и выполняет отправку данных, эмулирующих протез.
 */
class ControllerEmulator {
    private val client: MqttClient = MqttClient();
    private val logger: Logger = LogManager.getLogger(ControllerEmulator::class.java.name)

    /**
     * Запуск эмулятора. Выполняет подключение к Mqtt брокеру и начинает прием сообщений.
     */
    fun start() {
        client.getDataObservable().subscribeBy(onNext = {
            this.receiveDataHandler(it)
        }, onError = {
            logger.error(it.message)
        }, onComplete = {
            logger.info("dataSubject complete")
        })

        client.start()
    }

    /**
     * Эмуляция обработки входных данных на контроллере.
     */
    private fun receiveDataHandler(data: MqttDataModel) {
        logger.info("receiveDataHandler start on topic [${data.topic}] and data [${data.data.size} bytes]")
        logger.info("Data content string - ${data.data.toString(java.nio.charset.Charset.defaultCharset())}")
    }
}