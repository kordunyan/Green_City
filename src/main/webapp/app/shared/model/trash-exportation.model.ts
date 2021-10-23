import dayjs from 'dayjs';
import { IOsbb } from 'app/shared/model/osbb.model';
import { IFullTrashImages } from 'app/shared/model/full-trash-images.model';
import { IEmptyTrashImages } from 'app/shared/model/empty-trash-images.model';

export interface ITrashExportation {
  id?: number;
  weight?: number | null;
  date?: string | null;
  trash_type?: string | null;
  action?: string | null;
  is_wash?: boolean | null;
  osbb?: IOsbb | null;
  fullTrashImages?: IFullTrashImages[] | null;
  emptyTrashImages?: IEmptyTrashImages[] | null;
}

export const defaultValue: Readonly<ITrashExportation> = {
  is_wash: false,
};
