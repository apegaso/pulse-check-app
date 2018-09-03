import { IEventPulse } from 'app/shared/model//event-pulse.model';

export interface IParticipantPulse {
    id?: number;
    events?: IEventPulse[];
}

export class ParticipantPulse implements IParticipantPulse {
    constructor(public id?: number, public events?: IEventPulse[]) {}
}
