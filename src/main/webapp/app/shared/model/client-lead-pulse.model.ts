import { IEventPulse } from 'app/shared/model//event-pulse.model';

export interface IClientLeadPulse {
    id?: number;
    userExtId?: number;
    events?: IEventPulse[];
}

export class ClientLeadPulse implements IClientLeadPulse {
    constructor(public id?: number, public userExtId?: number, public events?: IEventPulse[]) {}
}
