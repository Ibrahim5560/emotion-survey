export interface IMessages {
  id?: number;
  counter?: number;
  trsId?: number;
  userId?: number;
  message?: string;
  status?: number;
  applicantName?: string;
  centerId?: number;
  systemId?: number;
  systemServicesId?: number;
  usersId?: number;
}

export const defaultValue: Readonly<IMessages> = {};
