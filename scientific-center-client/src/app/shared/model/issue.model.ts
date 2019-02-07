import { Journal } from './journal.model';
import { Paper } from './paper.model';

export class Issue {
    id: number;
    year: number;
    month: string;
    journal: Journal;
    papers: Paper[];
}