export interface IMessageFeedback {
  id?: number;
  systemId?: number;
  centerId?: number;
  systemServicesId?: number;
  counter?: number;
  trsId?: number;
  userId?: number;
  message?: string;
  status?: number;
  feedback?: string;
  applicantName?: string;
}

export const defaultValue: Readonly<IMessageFeedback> = {};
