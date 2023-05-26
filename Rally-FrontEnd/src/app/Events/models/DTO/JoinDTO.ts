import { Event } from "../event";

export class JoinDTO {
    id: number;
    event: Event;
    name: string;
    contactEmail: string;
    numAttending: number;   
    comment: string;

    constructor( id: number, event: Event, name: string, contactEmail: string, numAttending: number, comment: string) {
        this.id = id;
        this.event = event;
        this.name = name;
        this.contactEmail = contactEmail;
        this.numAttending = numAttending;
        this.comment = comment;
    }



}