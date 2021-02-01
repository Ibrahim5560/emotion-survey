import { IMessages } from 'app/shared/model/messages.model';

export interface ISystemServices {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  systemServicesMessages?: IMessages[];
  systemId?: number;
}

export const defaultValue: Readonly<ISystemServices> = {};
