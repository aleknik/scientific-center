import { Author } from './author.model';
import { Journal } from './journal.model';
import { ScienceField } from './science-field.model';

export class Paper {
    title: string;
    paperAbstract: string;
    keywords: string[];
    scienceField: ScienceField;
    coauthors: Author[];
    price: number;
    author: Author;
    journal: Journal;
}
