import { IMessages } from 'app/shared/model/messages.model';

export interface ICenter {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  centerMessages?: IMessages[];
}

export const defaultValue: Readonly<ICenter> = {};
