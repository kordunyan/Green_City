import dayjs from 'dayjs';
import { IOsbb } from 'app/shared/model/osbb.model';

export interface ITrashExportation {
  id?: number;
  weight?: number | null;
  date?: string | null;
  trash_type?: string | null;
  action?: string | null;
  osbb?: IOsbb | null;
}

export const defaultValue: Readonly<ITrashExportation> = {};
