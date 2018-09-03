import { IEventPulse } from 'app/shared/model//event-pulse.model';

export interface IClientLeadPulse {
    id?: number;
    events?: IEventPulse[];
}

export class ClientLeadPulse implements IClientLeadPulse {
    constructor(public id?: number, public events?: IEventPulse[]) {}
}
