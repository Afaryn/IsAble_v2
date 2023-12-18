import tensorflow as tf


class MyCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs=None):
        if logs is None:
            logs = {}
        target_accuracy = 0.97
        target_loss = 0.2

        current_accuracy = logs.get('accuracy')
        current_val_accuracy = logs.get('val_accuracy')
        current_loss = logs.get('loss')

        if (current_accuracy is not None and current_accuracy > target_accuracy) and \
           (current_val_accuracy is not None and current_val_accuracy > target_accuracy) and \
           (current_loss is not None and current_loss < target_loss):
            self.model.stop_training = True