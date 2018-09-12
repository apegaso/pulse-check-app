import { Moment } from 'moment';

export interface IEmail {
    id?: number;
    from?: string;
    addresses?: string;
    subject?: string;
    body?: string;
    isHtml?: boolean;
    isSent?: boolean;
    dateInsert?: Moment;
    dateSent?: Moment;
}

export class Email implements IEmail {
    constructor(
        public id?: number,
        public from?: string,
        public addresses?: string,
        public subject?: string,
        public body?: string,
        public isHtml?: boolean,
        public isSent?: boolean,
        public dateInsert?: Moment,
        public dateSent?: Moment
    ) {
        this.isHtml = this.isHtml || false;
        this.isSent = this.isSent || false;
    }
}
