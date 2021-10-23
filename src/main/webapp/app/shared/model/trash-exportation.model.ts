import dayjs from 'dayjs';
import { IFullTrashImages } from 'app/shared/model/full-trash-images.model';
import { IEmptyTrashImages } from 'app/shared/model/empty-trash-images.model';
import { IOsbb } from 'app/shared/model/osbb.model';

export interface ITrashExportation {
  id?: number;
  weight?: number | null;
  date?: string | null;
  trash_type?: string | null;
  is_wash?: boolean | null;
  fullTrashImages?: IFullTrashImages[] | null;
  emptyTrashImages?: IEmptyTrashImages[] | null;
  osbb?: IOsbb | null;
}

export const defaultValue: Readonly<ITrashExportation> = {
  is_wash: false,
};
