import { Author } from './author.model';
import { Journal } from './journal.model';
import { ScienceField } from './science-field.model';
import { Reviewer } from './reviewer.model';

export class Paper {
    id: number;
    title: string;
    paperAbstract: string;
    keywords: string[];
    scienceField: ScienceField;
    coauthors: Author[];
    reviewers: Reviewer[];
    price: number;
    author: Author;
    journal: Journal;
}
