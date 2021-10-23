import { ITrashExportation } from 'app/shared/model/trash-exportation.model';

export interface IFullTrashImages {
  id?: number;
  path?: string | null;
  trashExportation?: ITrashExportation | null;
}

export const defaultValue: Readonly<IFullTrashImages> = {};
