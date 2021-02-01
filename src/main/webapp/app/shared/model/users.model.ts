import { IMessages } from 'app/shared/model/messages.model';

export interface IUsers {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  usersMessages?: IMessages[];
}

export const defaultValue: Readonly<IUsers> = {};
