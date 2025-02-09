import { Pipe, PipeTransform } from '@angular/core';
import { Gatito } from '../interface/gatito';

@Pipe({
  name: 'argazki',
  pure: true,
})
export class ArgazkiPipe implements PipeTransform {
  transform(gatito: Gatito): string {

    if (
      gatito?.imagen?.type === 'Buffer' &&
      Array.isArray(gatito.imagen.data)
    ) {
      const uint8Array = new Uint8Array(gatito.imagen.data);
      const binaryString = uint8Array.reduce(
        (data, byte) => data + String.fromCharCode(byte),
        ''
      );
      const base64String = btoa(binaryString)
      return `data:image/jpeg;base64,${base64String}`;
    }

    return 'img/no-image.jpg';
  }
}
