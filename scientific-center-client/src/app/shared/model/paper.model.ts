import { Author } from './author.model';
import { Journal } from './journal.model';

export class Paper {
    title: string;
    paperAbstract: string;
    keywords: string[];
    scienceField: string;
    coauthors: Author[];
    price: number;
    author: Author;
    journal: Journal;
}
