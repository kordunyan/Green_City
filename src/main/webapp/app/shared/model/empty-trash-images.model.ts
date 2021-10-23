import { ITrashExportation } from 'app/shared/model/trash-exportation.model';

export interface IEmptyTrashImages {
  id?: number;
  path?: string | null;
  trashExportation?: ITrashExportation | null;
}

export const defaultValue: Readonly<IEmptyTrashImages> = {};
