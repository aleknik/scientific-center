import { Journal } from './journal.model';

export class Issue {
    id: number;
    year: number;
    month: string;
    journal: Journal;
}