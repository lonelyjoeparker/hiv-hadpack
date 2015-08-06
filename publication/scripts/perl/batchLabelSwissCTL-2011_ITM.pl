opendir(DIR, $ARGV[0]);
@dirnames = readdir(DIR);
$i = 0;
foreach $filename(@dirnames){
	if(length($filename)>3){
		$i = sprintf("%02d",$i);
		$cmd = "perl labelSwiss-2011.pl JRFL_ITM_29Model_1.pdb ".$ARGV[0]."$filename $filename\n";
		print "$cmd\n";
		system(`$cmd`);
		$i++;
		
	}
}
closedir(DIR);